package com.sirdave.campusnavigator.presentation.screens

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Rect
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.sirdave.campusnavigator.R
import com.sirdave.campusnavigator.domain.model.DirectionWithIcon
import com.sirdave.campusnavigator.domain.model.PlaceData
import com.sirdave.campusnavigator.presentation.composables.*
import com.sirdave.campusnavigator.presentation.places.PlaceEvent
import com.sirdave.campusnavigator.presentation.places.PlaceState
import com.sirdave.campusnavigator.util.assignIconToDirection
import com.sirdave.campusnavigator.util.formatDistance
import com.sirdave.campusnavigator.util.formatTime
import kotlinx.coroutines.*
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.Road
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: PlaceState,
    padding: PaddingValues,
    onEvent: (PlaceEvent) -> Unit,
    onViewFullScreen: (PlaceData) -> Unit,
    onBackClicked: () -> Unit
){
    val context = LocalContext.current
    val road = state.road
    var allDirections = emptyList<DirectionWithIcon>()
    road?.let { allDirections = getDirections(it) }

    var currentScreen by remember { mutableStateOf(BottomSheetContent.Detail) }
    val scaffoldSheetState = rememberBottomSheetScaffoldState()
    val bottomPadding = padding.calculateBottomPadding() + 40.dp
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldSheetState,
        sheetPeekHeight = bottomPadding,
        modifier = Modifier.padding(padding),
        sheetContent = {
            when (currentScreen){
                BottomSheetContent.Detail -> {
                    coroutineScope.launch { scaffoldSheetState.bottomSheetState.expand() }

                    DestinationDetail(
                        state = state,
                        onViewFullScreen = onViewFullScreen,
                        onBackClicked =  onBackClicked,
                        onDirect = { currentScreen = BottomSheetContent.CommuteModes }
                    )
                }

                BottomSheetContent.CommuteModes -> {
                    DirectionsBar(
                        placeState = state,
                        onBackClick = { currentScreen = BottomSheetContent.Detail },
                        onWalkSelected = { onEvent(PlaceEvent.OnCommuteModeChanged(OSRMRoadManager.MEAN_BY_FOOT)) },
                        onCarSelected = { onEvent(PlaceEvent.OnCommuteModeChanged(OSRMRoadManager.MEAN_BY_CAR)) },
                        onBikeSelected = { onEvent(PlaceEvent.OnCommuteModeChanged(OSRMRoadManager.MEAN_BY_BIKE)) },
                        onStartClicked = {
                            onEvent(PlaceEvent.GetDirections(state.selectedMode))
                            currentScreen = BottomSheetContent.SelectedMode
                            onEvent(PlaceEvent.ToggleRoadDirections(true))
                        }
                    )
                }

                BottomSheetContent.SelectedMode -> {
                    SelectedCommuteBar(
                        placeState = state,
                        onExit = {
                            currentScreen = BottomSheetContent.CommuteModes
                            onEvent(PlaceEvent.ToggleRoadDirections(false))
                         },
                        onWalkSelected = { onEvent(PlaceEvent.GetDirections(OSRMRoadManager.MEAN_BY_FOOT)) },
                        onCarSelected = { onEvent(PlaceEvent.GetDirections(OSRMRoadManager.MEAN_BY_CAR)) },
                        onBikeSelected = { onEvent(PlaceEvent.GetDirections(OSRMRoadManager.MEAN_BY_BIKE)) }
                    )
                }
            }

        },
    ){
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(modifier = Modifier.fillMaxSize()){
                AndroidView(
                    modifier = Modifier.fillMaxWidth(),
                    factory = { context ->
                        MapView(context).apply {
                            Configuration.getInstance().load(
                                context,
                                getSharedPreferences(context)
                            )
                            setTileSource(TileSourceFactory.MAPNIK)
                            mapCenter
                            setMultiTouchControls(true)
                            getLocalVisibleRect(Rect())
                            val controller = controller

                            val currentLocation = state.lastKnownLocation
                            currentLocation?.let {location ->
                                val mapPoint = GeoPoint(location.latitude, location.longitude)
                                controller.setZoom(18.0)
                                controller.animateTo(mapPoint)

                                val startMarker = Marker(this)
                                startMarker.position = mapPoint
                                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            }
                        }
                    },
                    update = { view ->
                        road?.let { r ->
                            val roadOverlay = RoadManager.buildRoadOverlay(r)
                            val roadMarkers = getRoadMarkers(context = context, mapView = view,
                                road = r, currentPlace = state.currentPlace?.name)

                            view.overlays.clear()
                            view.overlays.addAll(roadMarkers)
                            view.overlays.add(roadOverlay)
                            view.invalidate()
                        }

                    }
                )
                if (allDirections.isNotEmpty() && state.showRoad){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .background(Color.Black.copy(alpha = 0.5f))
                    ) {
                        DirectionsToggleCard(directions = allDirections)
                    }
                }
            }

        }
    }
}


private fun getRoadMarkers(
    context: Context,
    mapView: MapView,
    road: Road,
    currentPlace: String?
): List<Marker>{
    val nodeMarkers = arrayListOf<Marker>()
    val startIcon = context.getDrawable(R.drawable.baseline_location)
    val directionIcon = context.getDrawable(R.drawable.baseline_circle)
    val endIcon = context.getDrawable(R.drawable.baseline_location_on)

    for (i in road.mNodes.indices) {
        val node = road.mNodes[i]
        val nodeMarker = Marker(mapView)
        nodeMarker.position = node.mLocation
        when (i) {
            0 -> {
                nodeMarker.icon = startIcon
                nodeMarker.title = "Start point"
                nodeMarker.snippet = "You are here"
            }
            road.mNodes.lastIndex -> {
                nodeMarker.icon = endIcon
                nodeMarker.title = currentPlace
            }
            else -> {
                nodeMarker.icon = directionIcon
                nodeMarker.title = "Step $i"
                nodeMarker.snippet = node.mInstructions
                nodeMarker.subDescription =
                    Road.getLengthDurationText(context, node.mLength, node.mDuration)
            }
        }
        nodeMarkers.add(nodeMarker)
    }
    return nodeMarkers
}

private fun getDirections(road: Road): List<DirectionWithIcon>{
    val directions = arrayListOf<DirectionWithIcon>()
    for (i in 1 until road.mNodes.size - 1) {
        val node = road.mNodes[i]

        val instructions = node.mInstructions
        if (!instructions.isNullOrEmpty()){
            var directionsWithIcon = assignIconToDirection(instructions)
            val time = formatTime(node.mDuration)
            val distance = formatDistance(node.mLength)
            directionsWithIcon = directionsWithIcon.copy(
                distanceToNextLocation = distance,
                timeToNextLocation = time
            )
            directions.add(directionsWithIcon)
        }
    }
    val lastDirection = DirectionWithIcon(
        text = "You have reached your destination",
        directionIcon = R.drawable.baseline_location_white
    )
    directions.add(lastDirection)
    return directions
}

private fun getSharedPreferences(context: Context) =
    context.getSharedPreferences(context.getString(R.string.app_name), MODE_PRIVATE)

enum class BottomSheetContent{
    Detail, CommuteModes, SelectedMode
}
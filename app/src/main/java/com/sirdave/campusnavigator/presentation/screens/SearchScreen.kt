package com.sirdave.campusnavigator.presentation.screens

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Rect
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.sirdave.campusnavigator.R
import com.sirdave.campusnavigator.domain.model.Place
import com.sirdave.campusnavigator.domain.model.PlaceData
import com.sirdave.campusnavigator.presentation.composables.DestinationDetail
import com.sirdave.campusnavigator.presentation.composables.DirectionsBar
import com.sirdave.campusnavigator.presentation.composables.Search
import com.sirdave.campusnavigator.presentation.composables.SelectedCommuteBar
import com.sirdave.campusnavigator.presentation.places.LocationEvent
import com.sirdave.campusnavigator.presentation.places.PlaceEvent
import com.sirdave.campusnavigator.presentation.places.PlaceState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
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
    locationEvent: Flow<LocationEvent>,
    onEvent: (PlaceEvent) -> Unit,
    onViewFullScreen: (PlaceData) -> Unit,
){
    val context = LocalContext.current
    var road by remember { mutableStateOf<Road?>(null) }


    LaunchedEffect(Unit){
        locationEvent.collect{ event ->
            when (event){
                is LocationEvent.Success ->{
                    road = event.road
                }

                is LocationEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    var currentScreen by remember { mutableStateOf(BottomSheetContent.Search) }
    val scaffoldSheetState = rememberBottomSheetScaffoldState()
    val bottomPadding = padding.calculateBottomPadding() + 40.dp

    var selectedPlace by remember { mutableStateOf<Place?>(null) }

    BottomSheetScaffold(
        scaffoldState = scaffoldSheetState,
        sheetPeekHeight = bottomPadding,
        modifier = Modifier.padding(padding),
        sheetContent = {
            when (currentScreen){
                BottomSheetContent.Search -> {
                    Search(
                        state = state,
                        onEvent = onEvent,
                        onViewDetails = { place ->
                            currentScreen = BottomSheetContent.Detail
                            selectedPlace = place
                        }
                    )
                }
                
                BottomSheetContent.Detail -> {
                    selectedPlace?.let {
                        DestinationDetail(
                            place = it,
                            onViewFullScreen = onViewFullScreen,
                            onBackClicked = { currentScreen = BottomSheetContent.Search },
                            onShowMapDirections = {},
                            onDirect = { currentScreen = BottomSheetContent.CommuteModes }
                        )
                    }
                }

                BottomSheetContent.CommuteModes -> {
                    DirectionsBar(
                        placeState = state,
                        onBackClick = { currentScreen = BottomSheetContent.Detail },
                        onWalkSelected = { onEvent(PlaceEvent.OnCommuteModeChanged(OSRMRoadManager.MEAN_BY_FOOT)) },
                        onCarSelected = { onEvent(PlaceEvent.OnCommuteModeChanged(OSRMRoadManager.MEAN_BY_CAR)) },
                        onBikeSelected = { onEvent(PlaceEvent.OnCommuteModeChanged(OSRMRoadManager.MEAN_BY_BIKE)) },
                        onStartClicked = { currentScreen = BottomSheetContent.SelectedMode }
                    )
                }

                BottomSheetContent.SelectedMode -> {
                    SelectedCommuteBar(
                        placeState = state,
                        onExit = { currentScreen = BottomSheetContent.CommuteModes },
                        onWalkSelected = { onEvent(PlaceEvent.OnCommuteModeChanged(OSRMRoadManager.MEAN_BY_FOOT)) },
                        onCarSelected = { onEvent(PlaceEvent.OnCommuteModeChanged(OSRMRoadManager.MEAN_BY_CAR)) },
                        onBikeSelected = { onEvent(PlaceEvent.OnCommuteModeChanged(OSRMRoadManager.MEAN_BY_BIKE)) }
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
                            controller.setZoom(6.0)
                            controller.animateTo(mapPoint)

                            val startMarker = Marker(this)
                            startMarker.position = mapPoint
                            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        }

                        val endPoint = GeoPoint(7.4, 3.89)

                        onEvent(
                            PlaceEvent.GetDirections(
                                endPoint.latitude,
                                endPoint.longitude,
                                state.selectedMode
                            )
                        )
                    }
                },
                update = { view ->
                    road?.let { r ->
                        val roadOverlay = RoadManager.buildRoadOverlay(r)
                        val roadMarkers = getRoadMarkers(context = context, mapView = view, road = r)
                        view.overlays.addAll(roadMarkers)
                        view.overlays.add(roadOverlay)
                    }

                }
            )
        }
    }
}

private fun getRoadMarkers(context: Context, mapView: MapView, road: Road): List<Marker>{
    val nodeMarkers = arrayListOf<Marker>()
    val startIcon = context.getDrawable(R.drawable.baseline_location)
    val directionIcon = context.getDrawable(R.drawable.baseline_circle)
    val endIcon = context.getDrawable(R.drawable.baseline_location_on)

    Log.d("SearchScreen", "total duration is ${road.mDuration}")
    Log.d("SearchScreen", "total length is ${road.mLength}")

    for (i in road.mNodes.indices) {
        val node = road.mNodes[i]
        val nodeMarker = Marker(mapView)
        nodeMarker.position = node.mLocation
        when (i) {
            0 -> {
                nodeMarker.icon = startIcon
                nodeMarker.title = "Start point"
            }
            road.mNodes.lastIndex -> {
                nodeMarker.icon = endIcon
                nodeMarker.title = "Destination"
            }
            else -> {
                nodeMarker.icon = directionIcon
                nodeMarker.title = "Step $i"
                nodeMarker.snippet = node.mInstructions
                nodeMarker.subDescription =
                    Road.getLengthDurationText(context, node.mLength, node.mDuration)
            }
        }

        Log.d("SearchScreen", "step is $i")
        Log.d("SearchScreen", "node instruction is ${node.mInstructions}")
        Log.d("SearchScreen", "node length is ${node.mLength}")
        Log.d("SearchScreen", "node duration is ${node.mDuration}")
        Log.d("SearchScreen", "<===================>")
        //nodeMarker.image = nodeIcon
        nodeMarkers.add(nodeMarker)
    }
    return nodeMarkers
}

private fun getSharedPreferences(context: Context) =
    context.getSharedPreferences(context.getString(R.string.app_name), MODE_PRIVATE)

enum class BottomSheetContent{
    Search, Detail, CommuteModes, SelectedMode
}
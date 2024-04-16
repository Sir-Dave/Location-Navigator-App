package com.sirdave.campusnavigator.presentation.screens

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Rect
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.sirdave.campusnavigator.R
import com.sirdave.campusnavigator.domain.model.Place
import com.sirdave.campusnavigator.domain.model.PlaceData
import com.sirdave.campusnavigator.presentation.composables.DestinationDetail
import com.sirdave.campusnavigator.presentation.composables.Search
import com.sirdave.campusnavigator.presentation.places.PlaceEvent
import com.sirdave.campusnavigator.presentation.places.PlaceState
import kotlinx.coroutines.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: PlaceState,
    padding: PaddingValues,
    onEvent: (PlaceEvent) -> Unit,
    onViewFullScreen: (PlaceData) -> Unit
){
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
                BottomSheetContent.Search ->
                    Search(
                        state = state, 
                        onEvent = onEvent,
                        onViewDetails = { place ->
                            currentScreen = BottomSheetContent.Detail
                            selectedPlace = place
                        }
                    )
                
                    BottomSheetContent.Detail -> {
                        selectedPlace?.let {
                            DestinationDetail(
                                place = it,
                                onViewFullScreen = onViewFullScreen,
                                onBackClicked = {
                                    currentScreen = BottomSheetContent.Search
                                }
                            )
                        }
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


                        val mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), this)
                        val controller = controller

                        mMyLocationOverlay.enableMyLocation()
                        mMyLocationOverlay.enableFollowLocation()
                        mMyLocationOverlay.isDrawAccuracyEnabled = true
                        mMyLocationOverlay.runOnFirstFix {
                            MainScope().launch {
                                runOnUiThread {
                                    controller.setCenter(mMyLocationOverlay.myLocation)
                                    controller.animateTo(mMyLocationOverlay.myLocation)
                                }
                            }
                        }
                        val currentLocation = state.lastKnownLocation
                        currentLocation?.let {location ->
                            val mapPoint = GeoPoint(location.latitude, location.longitude)
                            controller.setZoom(6.0)
                            controller.animateTo(mapPoint)
                        }
                        overlays.add(mMyLocationOverlay)
                    }
                },
                update = { view ->


                }
            )
        }
    }
}
private fun getSharedPreferences(context: Context) =
    context.getSharedPreferences(context.getString(R.string.app_name), MODE_PRIVATE)


suspend fun runOnUiThread(block: suspend () -> Unit) = withContext(Dispatchers.Main) { block() }

enum class BottomSheetContent{
    Search, Detail
}
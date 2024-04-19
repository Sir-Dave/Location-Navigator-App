package com.sirdave.campusnavigator.presentation.places

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.sirdave.campusnavigator.data.OSMRepository
import com.sirdave.campusnavigator.domain.repository.PlaceRepository
import com.sirdave.campusnavigator.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import org.osmdroid.util.GeoPoint
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val repository: PlaceRepository,
    private val osmRepository: OSMRepository
) : ViewModel(){

    var placeState by mutableStateOf(PlaceState())
    private var searchJob: Job? = null

    init {
        getAllPlaces()
    }

    @SuppressLint("MissingPermission")
    fun getDeviceLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    placeState = placeState.copy(
                        lastKnownLocation = task.result,
                    )
                    Log.d("ViewModel", "latitude and longitude are ${placeState.lastKnownLocation?.latitude} and  ${placeState.lastKnownLocation?.longitude} ")
                }
            }
        } catch (e: SecurityException) {
            // Show error or something
        }
    }

    private fun searchPlacesByName(name: String, fetchFromRemote: Boolean = true) {
        placeState = placeState.copy(isLoading = true)
        viewModelScope.launch {
            val placeResult = repository.searchPlacesByName(name, fetchFromRemote)
            placeResult.collect {
                withContext(Dispatchers.Main) {
                    when (val result = it) {

                        is Resource.Success -> {
                            placeState = placeState.copy(
                                isLoading = false,
                                error = null,
                                allPlaces = result.data!!
                            )
                        }

                        is Resource.Error -> {
                            placeState = placeState.copy(
                                isLoading = false,
                                error = result.message,
                                allPlaces = emptyList()
                            )
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun searchPlacesByType(type: String, fetchFromRemote: Boolean = true) {
        placeState = placeState.copy(isLoading = true)
        viewModelScope.launch {
            val placeResult = repository.searchPlacesByType(type = type, fetchFromRemote)
            placeResult.collect {
                withContext(Dispatchers.Main) {
                    when (val result = it) {

                        is Resource.Success -> {
                            placeState = placeState.copy(
                                isLoading = false,
                                error = null,
                                allPlaces = result.data!!
                            )
                        }

                        is Resource.Error -> {
                            placeState = placeState.copy(
                                isLoading = false,
                                error = result.message,
                                allPlaces = emptyList()
                            )
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun getAllPlaces(fetchFromRemote: Boolean = true) {
        placeState = placeState.copy(isLoading = true)
        viewModelScope.launch {
            val placeResult = repository.findAllPlaces(fetchFromRemote)
            placeResult.collect {
                withContext(Dispatchers.Main) {
                    when (val result = it) {

                        is Resource.Success -> {
                            placeState = placeState.copy(
                                isLoading = false,
                                error = null,
                                allPlaces = result.data!!
                            )
                        }

                        is Resource.Error -> {
                            placeState = placeState.copy(
                                isLoading = false,
                                error = result.message,
                                allPlaces = emptyList()
                            )
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun findPlaceById(id: Long, fetchFromRemote: Boolean = true) {
        placeState = placeState.copy(isLoading = true)
        viewModelScope.launch {
            val placeResult = repository.findPlaceById(id, fetchFromRemote)
            placeResult.collect {
                withContext(Dispatchers.Main) {
                    when (val result = it) {

                        is Resource.Success -> {
                            placeState = placeState.copy(
                                isLoading = false,
                                error = null,
                                currentPlace = result.data!!
                            )
                        }

                        is Resource.Error -> {
                            placeState = placeState.copy(
                                isLoading = false,
                                error = result.message,
                                currentPlace = null
                            )
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun getRoad(startPoint: GeoPoint, endPoint: GeoPoint, commuteMode: String){
        viewModelScope.launch {
            when (val roadResult = osmRepository.getRoad(startPoint, endPoint, commuteMode)) {
                is Resource.Success -> {
                    placeState = placeState.copy(
                        isLoading = false,
                        error = null,
                        road = roadResult.data!!
                    )
                }

                is Resource.Error -> {
                    placeState = placeState.copy(
                        isLoading = false,
                        error = roadResult.message,
                        road = null
                    )
                }
                else -> Unit
            }
        }
    }

    fun onEvent(event: PlaceEvent){
        when(event){
            is PlaceEvent.GetAllPlaces -> {
                getAllPlaces()
            }

            is PlaceEvent.GetCurrentPlace -> {
                findPlaceById(id = event.id)
            }

            is PlaceEvent.SearchPlacesByType -> {
                searchPlacesByType(type = event.type)
            }

            is PlaceEvent.OnSearchQueryChanged -> {
                placeState = placeState.copy(searchQuery = event.searchQuery)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500.milliseconds)
                    searchPlacesByName(name = placeState.searchQuery)
                }
            }

            is PlaceEvent.OnCommuteModeChanged -> {
                placeState = placeState.copy(selectedMode = event.commuteMode)
            }

            is PlaceEvent.GetDirections -> {
                placeState = placeState.copy(selectedMode = event.commuteMode)
                val currentLocation = placeState.lastKnownLocation
                val currentPlace = placeState.currentPlace
                val selectedMode = event.commuteMode

                if (currentLocation != null && currentPlace != null){
                    val startPoint = GeoPoint(currentLocation.latitude, currentLocation.longitude)
                    val endPoint = GeoPoint(currentPlace.latitude, currentPlace.longitude)
                    getRoad(startPoint, endPoint, selectedMode)
                }
            }

            is PlaceEvent.OnPlaceSelected -> {
                placeState = placeState.copy(currentPlace = event.place)
                val currentLocation = placeState.lastKnownLocation

                if (currentLocation != null){
                    val startPoint = GeoPoint(currentLocation.latitude, currentLocation.longitude)
                    val endPoint = GeoPoint(event.place.latitude, event.place.longitude)
                    getRoad(startPoint, endPoint, commuteMode = placeState.selectedMode)
                }
            }

            is PlaceEvent.ToggleRoadDirections -> {
                placeState = placeState.copy(showRoad = event.showRoad)
            }
        }
    }
}

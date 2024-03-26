package com.sirdave.campusnavigator.presentation.places

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirdave.campusnavigator.domain.repository.PlaceRepository
import com.sirdave.campusnavigator.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val repository: PlaceRepository) : ViewModel(){

    var placeState by mutableStateOf(PlaceState())
    var searchJob: Job? = null

    init {
        getAllPlaces()
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
        }
    }
}

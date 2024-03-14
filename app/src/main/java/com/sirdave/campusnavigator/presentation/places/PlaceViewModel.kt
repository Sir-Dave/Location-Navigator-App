package com.sirdave.campusnavigator.presentation.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirdave.campusnavigator.domain.repository.PlaceRepository
import com.sirdave.campusnavigator.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val repository: PlaceRepository) : ViewModel(){

    private val _placeState = MutableStateFlow(PlaceState())
    val placeState: StateFlow<PlaceState> = _placeState

    init {
        getAllPlaces()
    }

    private fun searchPlacesByName(name: String, fetchFromRemote: Boolean = true) {
        _placeState.value = _placeState.value.copy(isLoading = true)
        viewModelScope.launch {
            val placeResult = repository.searchPlacesByName(name, fetchFromRemote)
            placeResult.collect {
                withContext(Dispatchers.Main) {
                    when (val result = it) {

                        is Resource.Success -> {
                            _placeState.value = _placeState.value.copy(
                                isLoading = false,
                                error = null,
                                allPlaces = result.data!!
                            )
                        }

                        is Resource.Error -> {
                            _placeState.value = _placeState.value.copy(
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
        _placeState.value = _placeState.value.copy(isLoading = true)
        viewModelScope.launch {
            val placeResult = repository.searchPlacesByType(type = type, fetchFromRemote)
            placeResult.collect {
                withContext(Dispatchers.Main) {
                    when (val result = it) {

                        is Resource.Success -> {
                            _placeState.value = _placeState.value.copy(
                                isLoading = false,
                                error = null,
                                allPlaces = result.data!!
                            )
                        }

                        is Resource.Error -> {
                            _placeState.value = _placeState.value.copy(
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
        _placeState.value = _placeState.value.copy(isLoading = true)
        viewModelScope.launch {
            val placeResult = repository.findAllPlaces(fetchFromRemote)
            placeResult.collect {
                withContext(Dispatchers.Main) {
                    when (val result = it) {

                        is Resource.Success -> {
                            _placeState.value = _placeState.value.copy(
                                isLoading = false,
                                error = null,
                                allPlaces = result.data!!
                            )
                        }

                        is Resource.Error -> {
                            _placeState.value = _placeState.value.copy(
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
        _placeState.value = _placeState.value.copy(isLoading = true)
        viewModelScope.launch {
            val placeResult = repository.findPlaceById(id, fetchFromRemote)
            placeResult.collect {
                withContext(Dispatchers.Main) {
                    when (val result = it) {

                        is Resource.Success -> {
                            _placeState.value = _placeState.value.copy(
                                isLoading = false,
                                error = null,
                                currentPlace = result.data!!
                            )
                        }

                        is Resource.Error -> {
                            _placeState.value = _placeState.value.copy(
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

            is PlaceEvent.SearchPlacesByName -> {
                searchPlacesByName(name = event.name)
            }

            is PlaceEvent.SearchPlacesByType -> {
                searchPlacesByType(type = event.type)
            }
        }
    }
}

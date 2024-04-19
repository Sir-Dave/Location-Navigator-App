package com.sirdave.campusnavigator.presentation.places

import com.sirdave.campusnavigator.domain.model.Place

sealed class PlaceEvent{
    data class GetCurrentPlace(val id: Long): PlaceEvent()
    data class SearchPlacesByType(val type: String): PlaceEvent()
    data class OnSearchQueryChanged(val searchQuery: String): PlaceEvent()
    data class OnCommuteModeChanged(val commuteMode: String): PlaceEvent()
    data class OnPlaceSelected(val place: Place): PlaceEvent()
    data class GetDirections(val commuteMode: String): PlaceEvent()
    data class ToggleRoadDirections(val showRoad: Boolean): PlaceEvent()
    object GetAllPlaces: PlaceEvent()
}
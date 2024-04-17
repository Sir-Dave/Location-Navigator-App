package com.sirdave.campusnavigator.presentation.places

sealed class PlaceEvent{
    data class GetCurrentPlace(val id: Long): PlaceEvent()
    data class SearchPlacesByType(val type: String): PlaceEvent()
    data class OnSearchQueryChanged(val searchQuery: String): PlaceEvent()
    //data class GetDirections(val commuteMode: String): PlaceEvent()
    data class GetDirections(val latitude: Double, val longitude: Double, val commuteMode: String): PlaceEvent()
    data class OnCommuteModeChanged(val commuteMode: String): PlaceEvent()

    object GetAllPlaces: PlaceEvent()
}
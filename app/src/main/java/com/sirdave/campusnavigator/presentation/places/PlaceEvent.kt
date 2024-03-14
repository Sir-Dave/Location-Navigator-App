package com.sirdave.campusnavigator.presentation.places

sealed class PlaceEvent{
    data class GetCurrentPlace(val id: Long): PlaceEvent()
    data class SearchPlacesByType(val type: String): PlaceEvent()
    data class SearchPlacesByName(val name: String): PlaceEvent()
    object GetAllPlaces: PlaceEvent()
}
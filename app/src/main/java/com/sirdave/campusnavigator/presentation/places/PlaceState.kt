package com.sirdave.campusnavigator.presentation.places

import com.sirdave.campusnavigator.domain.model.Place

data class PlaceState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val popularPlaces: List<Place> = emptyList(),
    val allPlaces: List<Place> = emptyList(),
    val currentPlace: Place? = null
)

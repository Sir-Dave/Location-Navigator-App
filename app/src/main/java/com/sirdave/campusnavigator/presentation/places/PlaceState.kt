package com.sirdave.campusnavigator.presentation.places

import android.location.Location
import com.sirdave.campusnavigator.domain.model.Place
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.Road

data class PlaceState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val popularPlaces: List<Place> = emptyList(),
    val allPlaces: List<Place> = emptyList(),
    val currentPlace: Place? = null,
    val lastKnownLocation: Location? = null,
    val road: Road? = null,
    val showRoad: Boolean = false,
    val selectedMode: String = OSRMRoadManager.MEAN_BY_CAR
)

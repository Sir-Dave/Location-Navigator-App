package com.sirdave.campusnavigator.presentation.places

import org.osmdroid.bonuspack.routing.Road

sealed class LocationEvent {
    data class Success(val road: Road): LocationEvent()
    data class Error(val message: String): LocationEvent()
}

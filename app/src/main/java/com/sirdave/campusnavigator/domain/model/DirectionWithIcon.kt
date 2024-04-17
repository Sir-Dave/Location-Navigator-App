package com.sirdave.campusnavigator.domain.model

import androidx.annotation.DrawableRes

data class DirectionWithIcon(
    val text: String,
    @DrawableRes val directionIcon: Int,
    val distanceToNextLocation: String = "",
    val timeToNextLocation: String = ""
)

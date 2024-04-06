package com.sirdave.campusnavigator.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaceData(
    val place: Place,
    val imageIndex: Int
): Parcelable

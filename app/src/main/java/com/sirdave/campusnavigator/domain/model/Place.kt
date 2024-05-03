package com.sirdave.campusnavigator.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Place (
    val id: Long,
    var name: String,
    var alias: String,
    var longitude: Double,
    var latitude: Double,
    var createdAt: LocalDateTime,
    var imageUrls: List<String>,
    var category: String,
    var updatedAt: LocalDateTime?
): Parcelable

val places = listOf(
    Place(
        id = 1,
        name = "Talents Apartment, University of Ibadan",
        alias = "Talents",
        longitude = 0.0,
        latitude = 0.0,
        createdAt = LocalDateTime.now(),
        imageUrls = mutableListOf(""),
        category = "Halls of Residence",
        updatedAt = null
    )
)

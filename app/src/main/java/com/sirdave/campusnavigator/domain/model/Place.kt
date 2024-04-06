package com.sirdave.campusnavigator.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

enum class PlaceType(val title: String) {
    LECTURE_THEATRE("Lecture Theatre"),
    HALL_OF_RESIDENCE("Hall of Residence"),
    RESTAURANT("Restaurant"),
    FACULTY("Faculty"),
    LEISURE("Leisure"),
    SPORT_FACILITY("Sport Facility")
}

enum class HostelCategory(val title: String) {
    MALE("Male"),
    FEMALE("Female"),
    POSTGRADUATE("Postgraduate"),
    PRIVATE("Private")
}

@Parcelize
data class Place (
    val id: Long,
    var name: String,
    var alias: String,
    var longitude: Double,
    var latitude: Double,
    var placeType: String,
    var createdAt: LocalDateTime,
    var imageUrls: List<String>,
    var category: String?,
    var updatedAt: LocalDateTime?
): Parcelable

val places = listOf(
    Place(
        id = 1,
        name = "Talents Apartment, University of Ibadan",
        alias = "Talents",
        longitude = 0.0,
        latitude = 0.0,
        placeType = PlaceType.HALL_OF_RESIDENCE.title,
        createdAt = LocalDateTime.now(),
        imageUrls = mutableListOf(""),
        category = HostelCategory.PRIVATE.title,
        updatedAt = null
    )
)

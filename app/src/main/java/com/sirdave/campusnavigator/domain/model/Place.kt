package com.sirdave.campusnavigator.domain.model

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
)

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
    ),
    Place(
        id = 2,
        name = "Nnamdi Azikwe Hall, University of Ibadan",
        alias = "Zik",
        longitude = 0.0,
        latitude = 0.0,
        placeType = PlaceType.HALL_OF_RESIDENCE.title,
        createdAt = LocalDateTime.now(),
        imageUrls = mutableListOf(""),
        category = HostelCategory.MALE.title,
        updatedAt = null
    ),
    Place(
        id = 3,
        name = "Obafemi Awolowo Hall, University of Ibadan",
        alias = "Awo",
        longitude = 0.0,
        latitude = 0.0,
        placeType = PlaceType.HALL_OF_RESIDENCE.title,
        createdAt = LocalDateTime.now(),
        imageUrls = mutableListOf(""),
        category = HostelCategory.FEMALE.title,
        updatedAt = null
    ),
    Place(
        id = 4,
        name = "Tafawa Balewa, University of Ibadan",
        alias = "Tafawa Balewa",
        longitude = 0.0,
        latitude = 0.0,
        placeType = PlaceType.HALL_OF_RESIDENCE.title,
        createdAt = LocalDateTime.now(),
        imageUrls = mutableListOf(""),
        category = HostelCategory.POSTGRADUATE.title,
        updatedAt = null
    ),
    Place(
        id = 5,
        name = "Boluid Restuarant, Nnamdi Azikwe Hall, University of Ibadan",
        alias = "Boluid",
        longitude = 0.0,
        latitude = 0.0,
        placeType = PlaceType.RESTAURANT.title,
        createdAt = LocalDateTime.now(),
        imageUrls = mutableListOf(""),
        category = null,
        updatedAt = null
    ),
    Place(
        id = 6,
        name = "Klazz Restaurant, Lord Tedder Hall, University of Ibadan",
        alias = "Klazz",
        longitude = 0.0,
        latitude = 0.0,
        placeType = PlaceType.RESTAURANT.title,
        createdAt = LocalDateTime.now(),
        imageUrls = mutableListOf(""),
        category = null,
        updatedAt = null
    ),
    Place(
        id = 7,
        name = "Faculty Lecture Theatre, Faculty of Science, University of Ibadan",
        alias = "FLT",
        longitude = 0.0,
        latitude = 0.0,
        placeType = PlaceType.FACULTY.title,
        createdAt = LocalDateTime.now(),
        imageUrls = mutableListOf(""),
        category = null,
        updatedAt = null
    ),
    Place(
        id = 8,
        name = "Obafemi Awolowo Stadium, University of Ibadan",
        alias = "Awo Stadium",
        longitude = 0.0,
        latitude = 0.0,
        placeType = PlaceType.SPORT_FACILITY.title,
        createdAt = LocalDateTime.now(),
        imageUrls = mutableListOf(""),
        category = null,
        updatedAt = null
    ),
    Place(
        id = 9,
        name = "Student Union Building, University of Ibadan",
        alias = "SUB",
        longitude = 0.0,
        latitude = 0.0,
        placeType = PlaceType.SPORT_FACILITY.title,
        createdAt = LocalDateTime.now(),
        imageUrls = mutableListOf(""),
        category = null,
        updatedAt = null
    ),
    Place(
        id = 10,
        name = "Heritage Park, University of Ibadan",
        alias = "Heritage Park",
        longitude = 0.0,
        latitude = 0.0,
        placeType = PlaceType.LEISURE.title,
        createdAt = LocalDateTime.now(),
        imageUrls = mutableListOf(""),
        category = null,
        updatedAt = null
    ),
    Place(
        id = 11,
        name = "Love Garden, University of Ibadan",
        alias = "Love Garden",
        longitude = 0.0,
        latitude = 0.0,
        placeType = PlaceType.LEISURE.title,
        createdAt = LocalDateTime.now(),
        imageUrls = mutableListOf(""),
        category = null,
        updatedAt = null
    ),
    Place(
        id = 12,
        name = "Central Bank of Nigeria Building, University of Ibadan",
        alias = "CBN",
        longitude = 0.0,
        latitude = 0.0,
        placeType = PlaceType.LECTURE_THEATRE.title,
        createdAt = LocalDateTime.now(),
        imageUrls = mutableListOf(""),
        category = null,
        updatedAt = null
    ),
    Place(
        id = 13,
        name = "Kola Daisi Lecture Theatre, Computer Science department, University of Ibadan",
        alias = "KDLT",
        longitude = 0.0,
        latitude = 0.0,
        placeType = PlaceType.LECTURE_THEATRE.title,
        createdAt = LocalDateTime.now(),
        imageUrls = mutableListOf(""),
        category = null,
        updatedAt = null
    ),
    Place(
        id = 14,
        name = "Faculty Lecture Theatre, Faculty of the Social Science, University of Ibadan",
        alias = "FLT",
        longitude = 0.0,
        latitude = 0.0,
        placeType = PlaceType.FACULTY.title,
        createdAt = LocalDateTime.now(),
        imageUrls = mutableListOf(""),
        category = null,
        updatedAt = null
    )
)

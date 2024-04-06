package com.sirdave.campusnavigator.util

import com.sirdave.campusnavigator.domain.model.PlaceType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun LocalDateTime.toFormattedDate(): String{
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    return this.format(formatter)
}

fun String.toLocalDateTime(): LocalDateTime{
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    return LocalDateTime.parse(this, formatter)
}

fun getPlaceType(title: String): String{
    val allPlaceTypes = PlaceType.values()
    for (place in allPlaceTypes){
        if (title == place.title)
            return place.name
    }
    return ""
}
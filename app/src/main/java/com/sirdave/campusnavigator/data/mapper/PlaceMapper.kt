package com.sirdave.campusnavigator.data.mapper

import com.sirdave.campusnavigator.data.remote.dto.response.PlaceDto
import com.sirdave.campusnavigator.domain.model.Place
import com.sirdave.campusnavigator.util.toLocalDateTime

fun PlaceDto.toPlace(): Place{
    return Place(
        id = id,
        name = name,
        alias = alias,
        longitude = longitude,
        latitude = latitude,
        placeType = placeType,
        createdAt = createdAt.toLocalDateTime(),
        imageUrls = imageUrls,
        category = category,
        updatedAt = updatedAt?.toLocalDateTime()
    )
}
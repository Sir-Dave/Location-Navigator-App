package com.sirdave.campusnavigator.data.remote.dto.response

data class PlaceDto(
    val id: Long,
    var name: String,
    var alias: String,
    var longitude: Double,
    var latitude: Double,
    var category: String,
    var imageUrls: List<String>,
    var createdAt: String,
    var updatedAt: String?
)
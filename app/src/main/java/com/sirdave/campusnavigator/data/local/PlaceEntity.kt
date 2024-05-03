package com.sirdave.campusnavigator.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlaceEntity (
    @PrimaryKey val id: Long,
    var name: String,
    var alias: String,
    var longitude: Double,
    var latitude: Double,
    var placeType: String,
    var createdAt: String,
    var imageUrls: List<String>,
    var category: String?,
    var updatedAt: String?
)
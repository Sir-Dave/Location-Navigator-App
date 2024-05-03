package com.sirdave.campusnavigator.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NavigatorDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOnePlace(place: PlaceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlaces(places: List<PlaceEntity>)

    @Query("SELECT * FROM placeentity WHERE id = :placeId")
    suspend fun getOnePlace(placeId: Long): PlaceEntity?

    @Query("SELECT * FROM placeentity WHERE name LIKE '%' || :name || '%'")
    suspend fun getPlacesByName(name: String): List<PlaceEntity>

    @Query("SELECT * FROM placeentity WHERE category LIKE '%' || :category || '%'")
    suspend fun getPlacesByCategory(category: String): List<PlaceEntity>

    @Query("SELECT * FROM placeentity")
    suspend fun getAllPlaces(): List<PlaceEntity>

    @Query("DELETE FROM placeentity")
    suspend fun clearAllPlaces()

}
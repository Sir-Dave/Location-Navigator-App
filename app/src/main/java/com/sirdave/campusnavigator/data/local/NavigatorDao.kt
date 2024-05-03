package com.sirdave.campusnavigator.data.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface NavigatorDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOnePlace(placeId: Long): PlaceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlaces(): List<PlaceEntity>

    @Query("SELECT * FROM placeentity WHERE id = :placeId")
    suspend fun getOnePlace(placeId: Long): PlaceEntity?

    @Query("SELECT * FROM placeentity")
    suspend fun getAllPlaces(): List<PlaceEntity>

    @Query("DELETE FROM placeentity")
    suspend fun clearAll()

}
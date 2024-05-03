package com.sirdave.campusnavigator.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        PlaceEntity::class,
    ],
    version = 1
)

@TypeConverters(Converters::class)
abstract class NavigatorDatabase: RoomDatabase() {
    abstract val dao : NavigatorDao
}
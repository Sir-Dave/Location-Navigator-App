package com.sirdave.campusnavigator.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        PlaceEntity::class,
    ],
    version = 1
)

abstract  class NavigatorDatabase: RoomDatabase() {
    abstract val dao : NavigatorDao
}
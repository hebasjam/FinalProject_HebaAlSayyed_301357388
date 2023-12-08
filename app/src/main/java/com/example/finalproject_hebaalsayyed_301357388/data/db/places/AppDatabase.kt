package com.example.finalproject_hebaalsayyed_301357388.data.db.places

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finalproject_hebaalsayyed_301357388.domain.dbo.places.PlaceEntity

@Database(entities = [PlaceEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placesDao(): PlacesDao
}

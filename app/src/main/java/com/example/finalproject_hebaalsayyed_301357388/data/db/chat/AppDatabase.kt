package com.example.finalproject_hebaalsayyed_301357388.data.db.chat

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finalproject_hebaalsayyed_301357388.domain.dbo.chat.ChatEntity

@Database(entities = [ChatEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
}


package com.example.finalproject_hebaalsayyed_301357388.data.db.chat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.finalproject_hebaalsayyed_301357388.domain.dbo.chat.ChatEntity

@Dao
interface ChatDao {
    @Insert
    suspend fun insertMessage(message: ChatEntity)

    @Query("SELECT * FROM chat_messages")
    suspend fun getAllMessages(): List<ChatEntity>

    @Query("DELETE FROM chat_messages")
    suspend fun clearMessages()

    @Query("SELECT * FROM (SELECT * FROM chat_messages ORDER BY id DESC LIMIT 10) AS sub ORDER BY sub.id ASC")
    suspend fun getLastTenMessages(): List<ChatEntity>
}

package com.example.finalproject_hebaalsayyed_301357388.domain.dbo.chat

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_messages")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sender: String,
    val message: String
)
package com.example.finalproject_hebaalsayyed_301357388.domain.dbo.places

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "latitude")
    val latitude: Double,

    @ColumnInfo(name = "longitude")
    val longitude: Double,

    @ColumnInfo(name = "formatted_address")
    val formattedAddress: String,

    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "city")
    val city: String

)
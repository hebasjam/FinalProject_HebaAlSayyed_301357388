package com.example.finalproject_hebaalsayyed_301357388.domain.repositories.places

import com.example.finalproject_hebaalsayyed_301357388.data.db.places.PlacesDao
import com.example.finalproject_hebaalsayyed_301357388.data.network.places.PlacesAPIService
import com.example.finalproject_hebaalsayyed_301357388.domain.dbo.places.PlaceEntity
import javax.inject.Inject

class PlacesRepository @Inject constructor(
    private val placesAPIService: PlacesAPIService,
    private val placesDao: PlacesDao
) {
    suspend fun getPlaces(query: String) = placesAPIService.getPlaces(query)

    suspend fun savePlace(placeEntity: PlaceEntity) {
        placesDao.insertPlace(placeEntity)
    }

    suspend fun getPlacesByCategoryAndCity(category: String, city:String): List<PlaceEntity> {
        return placesDao.getPlacesByCategoryAndCity(category,city)
    }

}
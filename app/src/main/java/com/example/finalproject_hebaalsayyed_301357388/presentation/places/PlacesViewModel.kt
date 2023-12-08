package com.example.finalproject_hebaalsayyed_301357388.presentation.places

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_hebaalsayyed_301357388.domain.dbo.places.PlaceEntity
import com.example.finalproject_hebaalsayyed_301357388.domain.models.request.places.PlacesApiResponse
import com.example.finalproject_hebaalsayyed_301357388.domain.repositories.places.PlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(private val repository: PlacesRepository) : ViewModel() {
    private val _placesData = MutableLiveData<PlacesApiResponse>()
    val placesData: LiveData<PlacesApiResponse> = _placesData

    private val _placesFromDB = MutableLiveData<List<PlaceEntity>>()
    val placesFromDB: LiveData<List<PlaceEntity>> = _placesFromDB

    fun fetchPlaces(city: String, category: String) {
        val query = "$category in $city"
        viewModelScope.launch {
            try {
                val places = repository.getPlaces(query)
                _placesData.postValue(places)
                savePlacesData(places,category,city)
            } catch (e: Exception) {
                Log.d("PlacesViewModel", e.message.toString())
            }
        }
    }

    fun fetchPlacesByCategory(category: String,city:String) {
        viewModelScope.launch {
            try {
                val savedPlaces = repository.getPlacesByCategoryAndCity(category,city)
                _placesFromDB.postValue(savedPlaces)
            } catch (e: Exception) {
                Log.d("PlacesViewModel", "Error fetching places by category: ${e.message}")
            }
        }
    }


    private fun savePlacesData(placesApiResponse: PlacesApiResponse,category: String,city:String) {
        viewModelScope.launch {
            placesApiResponse.results.forEach { placeResult ->
                val placeEntity = PlaceEntity(
                    name = placeResult.name,
                    latitude = placeResult.geometry.location.lat,
                    longitude = placeResult.geometry.location.lng,
                    formattedAddress = placeResult.formatted_address,
                    category = category,
                    city = city
                )
                repository.savePlace(placeEntity)
            }
        }
    }
}

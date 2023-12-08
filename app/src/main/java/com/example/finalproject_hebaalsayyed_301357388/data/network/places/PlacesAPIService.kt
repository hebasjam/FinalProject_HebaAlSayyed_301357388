package com.example.finalproject_hebaalsayyed_301357388.data.network.places

import com.example.finalproject_hebaalsayyed_301357388.domain.models.request.places.PlacesApiResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface PlacesAPIService {
    //get query from user and return list of places
    @GET("api/place/textsearch/json")
    suspend fun getPlaces(@Query("query") query: String, @Query("key") apiKey: String = "AIzaSyD8ys_MCMj3eQ7i9DdvEOKvIsrOMjDAe3U"): PlacesApiResponse
}
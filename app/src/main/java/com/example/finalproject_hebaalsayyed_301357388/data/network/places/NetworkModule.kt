package com.example.finalproject_hebaalsayyed_301357388.data.network.places

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    val baseUrl = "https://maps.googleapis.com/maps/"

    //Provide retrofit instance
    @Provides
    @Singleton
    @Named("PlacesRetrofit")
    fun providePlacesRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Provide API service
    @Provides
    @Singleton
    fun provideGoogleAPIService(@Named("PlacesRetrofit") retrofit: Retrofit): PlacesAPIService {
        return retrofit.create(PlacesAPIService::class.java)
    }
}
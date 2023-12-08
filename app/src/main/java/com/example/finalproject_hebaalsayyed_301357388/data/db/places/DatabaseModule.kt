package com.example.finalproject_hebaalsayyed_301357388.data.db.places

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context, firebaseAuth: FirebaseAuth): AppDatabase {
        val user = firebaseAuth.currentUser
        val databaseName = if (user != null) "Places_db_${user.uid}.db" else "Places.db"

        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            databaseName
        ).build()
    }

    @Provides
    fun providePlacesDao(database: AppDatabase): PlacesDao {
        return database.placesDao()
    }
}

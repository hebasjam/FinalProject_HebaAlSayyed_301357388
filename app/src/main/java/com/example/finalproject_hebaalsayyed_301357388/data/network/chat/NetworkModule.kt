package com.example.finalproject_hebaalsayyed_301357388.data.network.chat

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val baseUrl = "https://api.openai.com/v1/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor{ chain ->
                val requestWithHeaders = chain.request().newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer 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")
                    .build()
                chain.proceed(requestWithHeaders)
            }.build()
    }

    @Provides
    @Singleton
    @Named("ChatRetrofit")
    fun provideRetrofit(okHttpClient: OkHttpClient, moshiConverterFactory: MoshiConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(moshiConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideChatGPT(@Named("ChatRetrofit") retrofit: Retrofit): GPTDataSource {
        return retrofit.create(GPTDataSource::class.java)
    }


    //provide moshi
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }


    //provides converter factory for moshi
    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }
}
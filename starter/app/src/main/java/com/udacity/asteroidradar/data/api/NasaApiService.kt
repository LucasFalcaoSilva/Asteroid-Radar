package com.udacity.asteroidradar.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import kotlinx.coroutines.Deferred
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


interface NasaApiService {

    @GET("planetary/apod")
    suspend fun getImageOfTheDay(
        @Query("api_key") apiKey: String
    ): Response<PictureOfDayResponse>

    @GET("neo/rest/v1/feed")
    suspend fun getNearEarthObject(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): Response<String>
}

object NasaApi {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            ScalarsConverterFactory.create()
        )
        .addConverterFactory(
            MoshiConverterFactory.create(
                moshi
            )
        )
        .baseUrl(Constants.BASE_URL)
        .build()

    val nasaService: NasaApiService = retrofit.create(
        NasaApiService::class.java
    )

}

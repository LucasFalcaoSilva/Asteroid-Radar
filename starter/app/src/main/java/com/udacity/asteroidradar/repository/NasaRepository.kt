package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.data.api.NasaApi
import com.udacity.asteroidradar.data.api.asDatabaseModel
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import com.udacity.asteroidradar.data.database.asDomainModel
import com.udacity.asteroidradar.domain.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NasaRepository(
    private val asteroidDatabase: AsteroidDatabase
) {

    val pictureOfDay: LiveData<PictureOfDay> =
        Transformations.map(
            asteroidDatabase.pictureDao.getPictureOfDay()
        ) {
            it?.asDomainModel()
        }

    suspend fun refreshPictureOfDay() {
        withContext(Dispatchers.IO) {
            try {
                NasaApi.nasaService.getImageOfTheDay(BuildConfig.NASA_KEY).apply {
                    if (isSuccessful) {
                        body()?.let {
                            asteroidDatabase.pictureDao.insert(it.asDatabaseModel())
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("refreshPictureOfDay", "Erro ao tentar obter PictureOfTheDay", e)
            }
        }
    }
}
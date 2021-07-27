package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.data.api.NasaApi
import com.udacity.asteroidradar.data.api.asDatabaseModel
import com.udacity.asteroidradar.data.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.database.NasaDatabase
import com.udacity.asteroidradar.data.database.asDomainModel
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.extensions.addSevenDays
import com.udacity.asteroidradar.extensions.currentFormatDate
import java.util.Calendar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class NasaRepository(
    private val nasaDatabase: NasaDatabase
) {

    val pictureOfDay: LiveData<PictureOfDay> =
        Transformations.map(
            nasaDatabase.pictureDao.getPictureOfDay()
        ) {
            it?.asDomainModel()
        }

    val asteroidList: LiveData<List<Asteroid>> =
        Transformations.map(
            nasaDatabase.nearEarthObjectDao.getNearEarthObjectList()
        ) { list ->
            list.map {
                it.asDomainModel()
            }
        }

    suspend fun refreshPictureOfDay() {
        withContext(Dispatchers.IO) {
            try {
                NasaApi.nasaService.getImageOfTheDay(
                    BuildConfig.NASA_KEY
                ).apply {
                    if (isSuccessful) {
                        body()?.let {
                            nasaDatabase.pictureDao.insert(it.asDatabaseModel())
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("refreshPictureOfDay", "Erro ao tentar obter PictureOfTheDay", e)
            }
        }
    }

    suspend fun refreshNearEarthObject() {
        withContext(Dispatchers.IO) {
            try {
                NasaApi.nasaService.getNearEarthObject(
                    Calendar.getInstance().currentFormatDate(),
                    Calendar.getInstance().addSevenDays().currentFormatDate(),
                    BuildConfig.NASA_KEY
                ).apply {
                    if (isSuccessful) {
                        body()?.let { response ->
                            nasaDatabase.nearEarthObjectDao.insertAll(
                                *parseAsteroidsJsonResult(JSONObject(response)).map {
                                    it.asDatabaseModel()
                                }.toTypedArray()
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("refreshPictureOfDay", "Erro ao tentar obter getNearEarthObject", e)
            }
        }
    }
}
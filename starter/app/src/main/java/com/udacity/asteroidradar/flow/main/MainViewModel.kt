package com.udacity.asteroidradar.flow.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.repository.AsteroidFilter
import com.udacity.asteroidradar.repository.NasaRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val nasaRepository: NasaRepository
) : ViewModel() {

    private val _navigateToSelectedProperty = MutableLiveData<Asteroid?>()
    val navigateToSelectedProperty: LiveData<Asteroid?>
        get() = _navigateToSelectedProperty

    val pictureOfDay = nasaRepository.pictureOfDay

    val asteroidList = nasaRepository.asteroidList

    init {
        _navigateToSelectedProperty.value = null
        viewModelScope.launch {
            nasaRepository.apply {
                onQueryAsteroidChanged(AsteroidFilter.SHOW_SAVED)
                refreshPictureOfDay()
                if (hasNearEarthObject()) {
                    searchSevenDaysNearEarthObject()
                }
            }
        }
    }

    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToSelectedProperty.value = asteroid
    }

    fun displayAsteroidDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    fun updateAsteroidFilter(filter: AsteroidFilter) {
        viewModelScope.launch {
            nasaRepository.onQueryAsteroidChanged(filter)
        }
    }

}
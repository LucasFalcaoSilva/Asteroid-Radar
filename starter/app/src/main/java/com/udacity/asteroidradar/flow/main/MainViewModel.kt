package com.udacity.asteroidradar.flow.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.repository.NasaRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val nasaRepository: NasaRepository
) : ViewModel() {

    val pictureOfDay = nasaRepository.pictureOfDay

    init {
        viewModelScope.launch {
            if (pictureOfDay.value == null) {
                nasaRepository.refreshPictureOfDay()
            }
        }
    }

}
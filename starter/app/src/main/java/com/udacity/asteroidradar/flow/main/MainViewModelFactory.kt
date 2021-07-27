package com.udacity.asteroidradar.flow.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.data.database.NasaDatabase
import com.udacity.asteroidradar.repository.NasaRepository

class MainViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                NasaRepository(
                    NasaDatabase.getInstance(context)
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
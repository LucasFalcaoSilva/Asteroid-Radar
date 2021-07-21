package com.udacity.asteroidradar.domain

data class PictureOfDay(
    val url: String,
    val mediaType: String,
    val title: String,
    val explanation: String,
    val copyright: String,
    val date: String,
)


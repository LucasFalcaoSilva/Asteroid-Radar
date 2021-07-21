package com.udacity.asteroidradar.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.PictureOfDay

@Entity(tableName = "picture_day_table")
data class PictureOfDayEntity constructor(
    @PrimaryKey
    val date: String,
    val url: String,
    val title: String,
    val copyright: String,
    val mediaType: String,
    val explanation: String,
)

fun PictureOfDayEntity.asDomainModel() = PictureOfDay(
    url = this.url,
    date = this.date,
    title = this.title,
    mediaType = this.mediaType,
    copyright = this.copyright,
    explanation = this.explanation,
)
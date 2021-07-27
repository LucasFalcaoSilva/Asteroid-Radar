package com.udacity.asteroidradar.data.api

import android.os.Parcelable
import com.squareup.moshi.Json
import com.udacity.asteroidradar.data.database.PictureOfDayEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PictureOfDayResponse(
    @Json(name = "service_version") var serviceVersion: String,
    @Json(name = "media_type") var mediaType: String,
    var explanation: String,
    var copyright: String,
    var hdurl: String,
    var title: String,
    var date: String,
    var url: String
) : Parcelable

fun PictureOfDayResponse.asDatabaseModel() =
    PictureOfDayEntity(
        url = this.url,
        date = this.date,
        title = this.title,
        mediaType = this.mediaType,
        copyright = this.copyright,
        explanation = this.explanation
    )


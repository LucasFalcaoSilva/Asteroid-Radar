package com.udacity.asteroidradar.extensions

import com.udacity.asteroidradar.Constants
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Calendar.currentFormatDate(): String =
    SimpleDateFormat(
        Constants.API_QUERY_DATE_FORMAT,
        Locale.getDefault()
    ).format(time)

fun Calendar.addSevenDays() =
    apply {
        add(Calendar.DAY_OF_YEAR, 7)
    }

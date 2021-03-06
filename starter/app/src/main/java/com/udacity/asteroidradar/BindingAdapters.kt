package com.udacity.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.flow.main.AsteroidAdapter

@BindingAdapter("loadPictureOfDay")
fun ImageView.bindPictureOfDay(pictureOfDay: PictureOfDay?) {
    pictureOfDay?.let {
        val imgUri = it.url.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(this)
    } ?: kotlin.run {
        setImageResource(
            R.drawable.ic_broken_image
        )
    }
}

@BindingAdapter("statusIcon")
fun ImageView.bindAsteroidStatusImage(isHazardous: Boolean) {
    setImageResource(
        if (isHazardous)
            R.drawable.ic_status_potentially_hazardous
        else
            R.drawable.ic_status_normal
    )
}

@BindingAdapter("asteroidStatusImage")
fun ImageView.bindDetailsStatusImage(isHazardous: Boolean) {
    setImageResource(
        if (isHazardous)
            R.drawable.asteroid_hazardous
        else
            R.drawable.asteroid_safe
    )
}

@BindingAdapter("astronomicalUnitText")
fun TextView.bindTextViewToAstronomicalUnit(number: Double) {
    text = String.format(
        context.getString(R.string.astronomical_unit_format),
        number
    )
}

@BindingAdapter("kmUnitText")
fun TextView.bindTextViewToKmUnit(number: Double) {
    text = String.format(
        context.getString(R.string.km_unit_format),
        number
    )
}

@BindingAdapter("velocityText")
fun TextView.bindTextViewToDisplayVelocity(number: Double) {
    text = String.format(
        context.getString(R.string.km_s_unit_format),
        number
    )
}

@BindingAdapter("listData")
fun RecyclerView.bindRecyclerView(data: List<Asteroid>?) {
    val adapter = adapter as AsteroidAdapter
    adapter.submitList(data)
}
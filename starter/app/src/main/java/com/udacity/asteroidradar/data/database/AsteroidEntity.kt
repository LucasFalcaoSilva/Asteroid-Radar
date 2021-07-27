package com.udacity.asteroidradar.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.Asteroid

@Entity(tableName = "asteroid_table")
data class AsteroidEntity constructor(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

fun AsteroidEntity.asDomainModel() = Asteroid(
    id = this.id,
    codename = this.codename,
    closeApproachDate = this.closeApproachDate,
    absoluteMagnitude = this.absoluteMagnitude,
    estimatedDiameter = this.estimatedDiameter,
    relativeVelocity = this.relativeVelocity,
    distanceFromEarth = this.distanceFromEarth,
    isPotentiallyHazardous = this.isPotentiallyHazardous
)
package com.udacity.asteroidradar.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Dao
interface PictureDao {

    @Query("SELECT * FROM picture_day_table")
    fun getListPictureOfDay(): LiveData<List<PictureOfDayEntity>>

    @Query("SELECT * FROM picture_day_table WHERE mediaType = 'image' ORDER BY date DESC LIMIT 1")
    fun getPictureOfDay(): LiveData<PictureOfDayEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pictureOfDayEntity: PictureOfDayEntity)

}

@Database(entities = [PictureOfDayEntity::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {

    abstract val pictureDao: PictureDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidDatabase? = null

        fun getInstance(context: Context): AsteroidDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        "asteroid_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}

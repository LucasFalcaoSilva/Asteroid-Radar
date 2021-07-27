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

@Dao
interface NearEarthObjectDao {

    @Query("SELECT * FROM asteroid_table")
    fun getNearEarthObjectList(): LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroidEntity: AsteroidEntity)

}

@Database(
    entities = [PictureOfDayEntity::class, AsteroidEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NasaDatabase : RoomDatabase() {

    abstract val pictureDao: PictureDao

    abstract val nearEarthObjectDao: NearEarthObjectDao

    companion object {
        @Volatile
        private var INSTANCE: NasaDatabase? = null

        fun getInstance(context: Context): NasaDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NasaDatabase::class.java,
                        "nasa_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}

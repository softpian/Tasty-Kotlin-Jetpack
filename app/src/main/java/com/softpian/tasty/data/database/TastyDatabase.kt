package com.softpian.tasty.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.softpian.tasty.data.database.entities.TastyFavoriteEntity
import com.softpian.tasty.data.database.entities.TastyRestaurantsEntity

@Database(
    entities = [TastyRestaurantsEntity::class, TastyFavoriteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TastyTypeConverter::class)
abstract class TastyDatabase : RoomDatabase() {

    abstract fun tastyDao(): TastyDao
}
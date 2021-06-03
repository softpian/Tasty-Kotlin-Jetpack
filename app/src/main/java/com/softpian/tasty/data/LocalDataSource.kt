package com.softpian.tasty.data

import android.util.Log
import com.softpian.tasty.data.database.TastyDao
import com.softpian.tasty.data.database.entities.TastyFavoriteEntity
import com.softpian.tasty.data.database.entities.TastyRestaurantsEntity
import com.softpian.tasty.models.yelp.Business
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val tastyDao: TastyDao,
) {
    // Room Database
    suspend fun insertTastyRestaurants(tastyRestaurantsEntity: TastyRestaurantsEntity) {
        tastyDao.insertTastyRestaurants(tastyRestaurantsEntity)
    }

    fun readTastyRestaurants(): Flow<List<TastyRestaurantsEntity>> {
        return tastyDao.readTastyRestaurants()
    }

    suspend fun insertFavorite(favoriteEntity: TastyFavoriteEntity) {
        tastyDao.insertFavorite(favoriteEntity)
    }

    suspend fun deleteFavorite(favoriteEntity: TastyFavoriteEntity) {
        tastyDao.deleteFavorite(favoriteEntity)
    }

    fun readFavorites(): Flow<List<TastyFavoriteEntity>> {
        return tastyDao.readFavorites()
    }
}
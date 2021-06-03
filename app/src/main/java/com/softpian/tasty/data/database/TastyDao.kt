package com.softpian.tasty.data.database

import androidx.room.*
import com.softpian.tasty.data.database.entities.TastyFavoriteEntity
import com.softpian.tasty.data.database.entities.TastyRestaurantsEntity
import com.softpian.tasty.models.yelp.Business
import kotlinx.coroutines.flow.Flow

@Dao
interface TastyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTastyRestaurants(tastyRestaurantsEntity: TastyRestaurantsEntity)

    @Query("SELECT * FROM tasty_restaurant_table ORDER BY id ASC")
    fun readTastyRestaurants(): Flow<List<TastyRestaurantsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: TastyFavoriteEntity)

    @Delete
    suspend fun deleteFavorite(favoriteEntity: TastyFavoriteEntity)

    @Query("SELECT * FROM tasty_favorite_table ORDER BY id ASC")
    fun readFavorites(): Flow<List<TastyFavoriteEntity>>
}
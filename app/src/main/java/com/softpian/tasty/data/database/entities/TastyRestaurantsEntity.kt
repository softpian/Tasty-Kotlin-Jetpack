package com.softpian.tasty.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.softpian.tasty.models.yelp.TastyRestaurant
import com.softpian.tasty.utils.Constants.Companion.TASTY_RESTAURANT_TABLE

@Entity(tableName = TASTY_RESTAURANT_TABLE)
class TastyRestaurantsEntity(
    var tastyRestaurant: TastyRestaurant
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
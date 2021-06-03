package com.softpian.tasty.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.softpian.tasty.models.yelp.Business
import com.softpian.tasty.models.yelp.TastyRestaurant

class TastyTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun tastyRestaurantToString(tastyRestaurant: TastyRestaurant): String {
        return gson.toJson(tastyRestaurant)
    }

    @TypeConverter
    fun stringToTastyRestaurant(data: String): TastyRestaurant {
        val type = object : TypeToken<TastyRestaurant>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun businessToString(business: Business): String {
        return gson.toJson(business)
    }

    @TypeConverter
    fun stringToBusiness(data: String): Business {
        val type = object : TypeToken<Business>() {}.type
        return gson.fromJson(data, type)
    }
}
package com.softpian.tasty.utils

import com.softpian.tasty.BuildConfig

class Constants {

    companion object {
        const val API_KEY = "Bearer ${BuildConfig.YELP_API_KEY}"
        const val BASE_URL = "https://api.yelp.com/v3/"

        const val TERM = "term"
        const val LOCATION = "location"

        const val BUSINESS_BUNDLE_KEY = "businessBundleKey"

        //Room
        const val DATABASE_NAME = "tasty_database"
        const val TASTY_RESTAURANT_TABLE = "tasty_restaurant_table"
        const val TASTY_FAVORITE_TABLE = "tasty_favorite_table"

        //Preferences Data Store
        const val PREFERENCES_NAME = "tasty_preferences"
        const val PREFERENCES_CHECKED_LOCATION = "checked_location"
        const val PREFERENCES_CHECKED_LOCATION_ID = "checked_location_id"
        const val PREFERENCES_CHECKED_FOOD_TYPE = "checked_food_type"
        const val PREFERENCES_CHECKED_FOOD_TYPE_ID = "checked_food_type_id"

        const val DEFAULT_LOCATION = "Auckland"
        const val DEFAULT_FOOD_TYPE = "restaurants"
    }
}
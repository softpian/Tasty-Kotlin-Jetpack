package com.softpian.tasty.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.softpian.tasty.models.yelp.Business
import com.softpian.tasty.utils.Constants.Companion.TASTY_FAVORITE_TABLE

@Entity(tableName = TASTY_FAVORITE_TABLE)
class TastyFavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var business: Business
)
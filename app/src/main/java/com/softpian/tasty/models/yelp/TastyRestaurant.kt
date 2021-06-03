package com.softpian.tasty.models.yelp


import com.google.gson.annotations.SerializedName

data class TastyRestaurant(
    @SerializedName("businesses")
    val businesses: List<Business>,
    @SerializedName("region")
    val region: Region,
    @SerializedName("total")
    val total: Int
)
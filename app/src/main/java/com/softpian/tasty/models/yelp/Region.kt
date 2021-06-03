package com.softpian.tasty.models.yelp


import com.google.gson.annotations.SerializedName

data class Region(
    @SerializedName("center")
    val center: Center
)
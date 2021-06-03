package com.softpian.tasty.models.yelp


import com.google.gson.annotations.SerializedName

data class Hour(
    @SerializedName("hours_type")
    val hoursType: String,
    @SerializedName("is_open_now")
    val isOpenNow: Boolean,
    @SerializedName("open")
    val `open`: List<Open>
)
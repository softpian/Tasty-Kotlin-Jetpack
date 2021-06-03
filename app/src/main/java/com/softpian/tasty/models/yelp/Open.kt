package com.softpian.tasty.models.yelp


import com.google.gson.annotations.SerializedName

data class Open(
    @SerializedName("day")
    val day: Int,
    @SerializedName("end")
    val end: String,
    @SerializedName("is_overnight")
    val isOvernight: Boolean,
    @SerializedName("start")
    val start: String
)
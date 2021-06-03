package com.softpian.tasty.models.yelp


import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("id")
    val id: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("time_created")
    val timeCreated: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("user")
    val user: User
)
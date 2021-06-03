package com.softpian.tasty.models.yelp


import com.google.gson.annotations.SerializedName

data class Reviews(
    @SerializedName("possible_languages")
    val possibleLanguages: List<String>,
    @SerializedName("reviews")
    val reviews: List<Review>,
    @SerializedName("total")
    val total: Int
)
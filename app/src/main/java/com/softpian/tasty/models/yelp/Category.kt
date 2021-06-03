package com.softpian.tasty.models.yelp


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    @SerializedName("alias")
    val alias: String,
    @SerializedName("title")
    val title: String
): Parcelable
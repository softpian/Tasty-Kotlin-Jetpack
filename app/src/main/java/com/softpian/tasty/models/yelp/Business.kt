package com.softpian.tasty.models.yelp


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Business(
    @SerializedName("alias")
    val alias: String,
    @SerializedName("categories")
    val categories: @RawValue List<Category>,
    @SerializedName("coordinates")
    val coordinates: @RawValue Coordinates,
    @SerializedName("display_phone")
    val displayPhone: String,
    @SerializedName("distance")
    val distance: Double,
    @SerializedName("id")
    val id: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("is_closed")
    val isClosed: Boolean,
    @SerializedName("location")
    val location: @RawValue Location,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("price")
    val price: String?,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("review_count")
    val reviewCount: Int,
    @SerializedName("transactions")
    val transactions: @RawValue List<Any>,
    @SerializedName("url")
    val url: String
): Parcelable
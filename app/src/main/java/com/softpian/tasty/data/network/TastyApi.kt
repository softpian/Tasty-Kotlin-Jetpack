package com.softpian.tasty.data.network

import com.softpian.tasty.models.yelp.Details
import com.softpian.tasty.models.yelp.Reviews
import com.softpian.tasty.models.yelp.TastyRestaurant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface TastyApi {

    @GET("businesses/search")
    suspend fun getRestaurants(
            @Header("Authorization") authHeader: String,
            @QueryMap queries: Map<String, String>
    ): Response<TastyRestaurant>

    @GET("businesses/{id}")
    suspend fun getRestaurantDetails(
        @Header("Authorization") authHeader: String,
        @Path("id") businessId: String
    ): Response<Details>

    @GET("businesses/{id}/reviews")
    suspend fun getRestaurantReviews(
        @Header("Authorization") authHeader: String,
        @Path("id") businessId: String
    ): Response<Reviews>
}
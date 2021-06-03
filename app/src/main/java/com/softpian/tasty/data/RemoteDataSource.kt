package com.softpian.tasty.data

import com.softpian.tasty.data.network.TastyApi
import com.softpian.tasty.models.yelp.Details
import com.softpian.tasty.models.yelp.Reviews
import com.softpian.tasty.models.yelp.TastyRestaurant
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val tastyApi: TastyApi
){
    suspend fun getRestaurant(
        authHeader: String,
        queries: Map<String, String>
    ): Response<TastyRestaurant> {
        return tastyApi.getRestaurants(authHeader, queries)
    }

    suspend fun getRestaurantDetails(
        authHeader: String,
        businessId: String
    ): Response<Details> {
        return tastyApi.getRestaurantDetails(authHeader, businessId)
    }

    suspend fun getRestaurantReviews(
        authHeader: String,
        businessId: String
    ): Response<Reviews> {
        return tastyApi.getRestaurantReviews(authHeader, businessId)
    }
}
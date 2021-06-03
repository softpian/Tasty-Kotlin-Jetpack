package com.softpian.tasty.viemodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.softpian.tasty.data.Repository
import com.softpian.tasty.data.database.entities.TastyFavoriteEntity
import com.softpian.tasty.data.database.entities.TastyRestaurantsEntity
import com.softpian.tasty.models.yelp.Business
import com.softpian.tasty.models.yelp.Details
import com.softpian.tasty.models.yelp.Reviews
import com.softpian.tasty.models.yelp.TastyRestaurant
import com.softpian.tasty.utils.NetworkResult
import com.softpian.tasty.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    // Retrofit
    val restaurantResponse: MutableLiveData<NetworkResult<TastyRestaurant>> = MutableLiveData()
    val restaurantDetailsResponse: MutableLiveData<NetworkResult<Details>> = MutableLiveData()
    val restaurantReviewsResponse: MutableLiveData<NetworkResult<Reviews>> = MutableLiveData()

    fun getRestaurants(authHeader: String, queries: Map<String, String>)
        = viewModelScope.launch {
            getRestaurantsSafeCall(authHeader, queries)
        }

    fun getRestaurantDetails(authHeader: String, businessId: String)
        = viewModelScope.launch {
            getRestaurantDetailsSafeCall(authHeader, businessId)
        }

    fun getRestaurantReviews(authHeader: String, businessId: String)
        = viewModelScope.launch {
            getRestaurantReviewsSafeCall(authHeader, businessId)
        }

    private suspend fun getRestaurantsSafeCall(authHeader: String, queries: Map<String, String>) {
        restaurantResponse.value = NetworkResult.Loading()
        if (Utils.isNetworkAvailable(getApplication())) {
            val response = repository.remote.getRestaurant(authHeader, queries)
            restaurantResponse.value = handleResponse(response)
            restaurantResponse.value?.data?.let {
                saveTastyRestaurantToDatabase(it)
            }
        } else {
            restaurantResponse.value = NetworkResult.Error("No Internet connection")
        }
    }

    private fun saveTastyRestaurantToDatabase(tastyRestaurant: TastyRestaurant) {
        val tastyRestaurantsEntity = TastyRestaurantsEntity(tastyRestaurant)
        insertTastyRestaurant(tastyRestaurantsEntity)
    }

    private suspend fun getRestaurantDetailsSafeCall(authHeader: String, businessId: String) {
        restaurantDetailsResponse.value = NetworkResult.Loading()
        val response = repository.remote.getRestaurantDetails(authHeader, businessId)
        restaurantDetailsResponse.value = handleResponse(response)
    }

    private suspend fun getRestaurantReviewsSafeCall(authHeader: String, businessId: String) {
        restaurantReviewsResponse.value = NetworkResult.Loading()
        val response = repository.remote.getRestaurantReviews(authHeader, businessId)
        restaurantReviewsResponse.value = handleResponse(response)
    }

    private fun <T> handleResponse(response: Response<T>): NetworkResult<T>? {
        when {
            response.isSuccessful -> {
                val responseBody = response.body()
                responseBody?.let {
                    return NetworkResult.Success(it)
                } ?: return NetworkResult.Error("Request failed")
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    // Room Database
    val readTastyRestaurants: LiveData<List<TastyRestaurantsEntity>> = repository.local.readTastyRestaurants().asLiveData()
    val readFavorites: LiveData<List<TastyFavoriteEntity>> = repository.local.readFavorites().asLiveData()

    private fun insertTastyRestaurant(tastyRestaurantsEntity: TastyRestaurantsEntity)
        = viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertTastyRestaurants(tastyRestaurantsEntity)
        }

    fun insertFavorite(business: Business) {
        val tastyFavoriteEntity = TastyFavoriteEntity(0, business)
        insertFavorite(tastyFavoriteEntity)
    }

    private fun insertFavorite(tastyFavoriteEntity: TastyFavoriteEntity)
        = viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavorite(tastyFavoriteEntity)
        }

    fun deleteFavorite(id: Int, business: Business) {
        val tastyFavoriteEntity = TastyFavoriteEntity(id, business)
        deleteFavorite(tastyFavoriteEntity)
    }

    private fun deleteFavorite(tastyFavoriteEntity: TastyFavoriteEntity)
        = viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavorite(tastyFavoriteEntity)
        }
}
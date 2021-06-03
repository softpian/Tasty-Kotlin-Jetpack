package com.softpian.tasty.viemodels

import android.app.Application
import androidx.lifecycle.*
import com.softpian.tasty.data.PreferencesDataStoreRepository
import com.softpian.tasty.utils.Constants
import com.softpian.tasty.utils.Constants.Companion.DEFAULT_FOOD_TYPE
import com.softpian.tasty.utils.Constants.Companion.DEFAULT_LOCATION
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val dataStoreRepository: PreferencesDataStoreRepository,
    application: Application
) : AndroidViewModel(application) {

    private var checkedLocation = DEFAULT_LOCATION
    private var checkedFoodType = DEFAULT_FOOD_TYPE

    // DataStore
    val checkedChipsFlow = dataStoreRepository.checkedChipsFlow

    fun writeCheckedChips(
        checkedLocation: String,
        checkedLocationId: Int,
        checkedFoodType: String,
        checkedFoodTypeId: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.writeCheckedChips(
            checkedLocation,
            checkedLocationId,
            checkedFoodType,
            checkedFoodTypeId
        )
    }

    fun buildQueries(): HashMap<String, String> {

        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            checkedChipsFlow.collect { checkedChips ->
                checkedLocation = checkedChips.checkedLocation
                checkedFoodType = checkedChips.checkedFoodType
            }
        }

        queries[Constants.TERM] = checkedFoodType
        queries[Constants.LOCATION] = checkedLocation

        return queries
    }
}
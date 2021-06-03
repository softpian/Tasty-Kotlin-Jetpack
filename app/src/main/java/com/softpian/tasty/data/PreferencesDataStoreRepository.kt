package com.softpian.tasty.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.softpian.tasty.models.CheckedChips
import com.softpian.tasty.utils.Constants.Companion.DEFAULT_FOOD_TYPE
import com.softpian.tasty.utils.Constants.Companion.DEFAULT_LOCATION
import com.softpian.tasty.utils.Constants.Companion.PREFERENCES_CHECKED_FOOD_TYPE
import com.softpian.tasty.utils.Constants.Companion.PREFERENCES_CHECKED_FOOD_TYPE_ID
import com.softpian.tasty.utils.Constants.Companion.PREFERENCES_CHECKED_LOCATION
import com.softpian.tasty.utils.Constants.Companion.PREFERENCES_CHECKED_LOCATION_ID
import com.softpian.tasty.utils.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCES_NAME)

@ActivityRetainedScoped
class PreferencesDataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object PreferencesKeys {
        val checkedLocation = stringPreferencesKey(PREFERENCES_CHECKED_LOCATION)
        val checkedLocationId = intPreferencesKey(PREFERENCES_CHECKED_LOCATION_ID)
        val checkedFoodType = stringPreferencesKey(PREFERENCES_CHECKED_FOOD_TYPE)
        val checkedFoodTypeId = intPreferencesKey(PREFERENCES_CHECKED_FOOD_TYPE_ID)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun writeCheckedChips(
        checkedLocation: String,
        checkedLocationId: Int,
        checkedFoodType: String,
        checkedFoodTypeId: Int
    ) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.checkedLocation] = checkedLocation
            preferences[PreferencesKeys.checkedLocationId] = checkedLocationId
            preferences[PreferencesKeys.checkedFoodType] = checkedFoodType
            preferences[PreferencesKeys.checkedFoodTypeId] = checkedFoodTypeId
        }
    }

    val checkedChipsFlow: Flow<CheckedChips> = dataStore.data
        .catch { exception ->
            when(exception) {
                is IOException -> emit(emptyPreferences())
                else -> throw exception
            }
        }
        .map { preferences ->
            val checkedLocation = preferences[PreferencesKeys.checkedLocation] ?: DEFAULT_LOCATION
            val checkedLocationId = preferences[PreferencesKeys.checkedLocationId] ?: 0
            val checkedFoodType = preferences[PreferencesKeys.checkedFoodType] ?: DEFAULT_FOOD_TYPE
            val checkedFoodTypeId = preferences[PreferencesKeys.checkedFoodTypeId] ?: 0
            CheckedChips(
                checkedLocation,
                checkedLocationId,
                checkedFoodType,
                checkedFoodTypeId
            )
        }
}

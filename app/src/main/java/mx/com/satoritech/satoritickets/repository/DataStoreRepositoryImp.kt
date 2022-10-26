package mx.com.satoritech.satoritickets.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImp @Inject constructor(
    private val dataStore: DataStore<Preferences>
){
    companion object {
        const val TUTORIAL_KEY = "TUTORIAL_KEY"
    }

    suspend fun saveTutorialState(value: Boolean){
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(TUTORIAL_KEY)] = value
        }
    }

    fun getTutorialState() = dataStore.data.map{ preferences ->
        preferences[booleanPreferencesKey(TUTORIAL_KEY)]?: false
    }
}
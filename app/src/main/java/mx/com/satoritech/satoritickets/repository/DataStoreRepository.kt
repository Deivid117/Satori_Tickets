package mx.com.satoritech.satoritickets.repository

//val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "on_boarding_pref")
//
//class DataStoreRepository(context: Context) {
//
//    private object PreferencesKey {
//        val onBoardingKey = booleanPreferencesKey(name = "on_boarding_completed")
//    }
//
//    private val dataStore = context.dataStore
//
//    suspend fun saveOnBoardingState(completed: Boolean) {
//        dataStore.edit { preferences ->
//            preferences[PreferencesKey.onBoardingKey] = completed
//        }
//    }
//
//    fun readOnBoardingState(): Flow<Boolean> {
//        return dataStore.data
//            .catch { exception ->
//                if (exception is IOException) {
//                    emit(emptyPreferences())
//                } else {
//                    throw exception
//                }
//            }
//            .map { preferences ->
//                val onBoardingState = preferences[PreferencesKey.onBoardingKey] ?: false
//                onBoardingState
//            }
//    }
//}
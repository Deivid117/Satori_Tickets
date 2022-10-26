package mx.com.satoritech.satoritickets.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import mx.com.satoritech.satoritickets.dataStore

abstract class DataStoreRep() {
    companion object {
        @JvmStatic
        fun newInstance(context: Context): DataStore<Preferences> {
            return context.dataStore
        }
    }
}
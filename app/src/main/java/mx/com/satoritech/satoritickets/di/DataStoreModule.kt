package mx.com.satoritech.satoritickets.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mx.com.satoritech.satoritickets.repository.DataStoreRep
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Singleton
    @Provides
    fun dataStoreProvider(
        @ApplicationContext context: Context
    ):DataStore<Preferences>{
        return DataStoreRep.newInstance(context)
    }
}
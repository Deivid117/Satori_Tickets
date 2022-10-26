package mx.com.satoritech.satoritickets.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import mx.com.satoritech.domain.models.User

interface LocalUserRepository {
    suspend fun upsert(user: User): Long
    fun get(): Flow<User?>
    fun getWithLiveData(): LiveData<User?>
    suspend fun deleteAll()
    suspend fun update(userDB: User)
}
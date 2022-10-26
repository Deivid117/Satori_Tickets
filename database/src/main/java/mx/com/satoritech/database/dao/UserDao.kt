package mx.com.satoritech.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import mx.com.satoritech.domain.models.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: User): Long

    @Query("SELECT * FROM users LIMIT 1")
    fun get(): Flow<User?>

    @Query("SELECT * FROM users LIMIT 1")
    fun getWithLiveData(): LiveData<User?>

    @Query("DELETE FROM users")
    suspend fun deleteAll()

    @Update
    suspend fun update(userDB: User)
}
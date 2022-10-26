package mx.com.satoritech.delega.colono.Repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import mx.com.satoritech.database.DBSatoritickets
import mx.com.satoritech.domain.models.User
import mx.com.satoritech.satoritickets.repository.LocalUserRepository
import javax.inject.Inject

class LocalUserRepositoryImp @Inject constructor(
    val dbAppUnitrips: DBSatoritickets
) : LocalUserRepository {
    override suspend fun upsert(user: User): Long {
        return dbAppUnitrips.userDao().upsert(user)
    }

    override fun get(): Flow<User?> {
        return dbAppUnitrips.userDao().get()
    }

    override fun getWithLiveData(): LiveData<User?> {
        return dbAppUnitrips.userDao().getWithLiveData()
    }

    override suspend fun deleteAll() {
        return dbAppUnitrips.userDao().deleteAll()
    }

    override suspend fun update(userDB: User) {
        return dbAppUnitrips.userDao().update(userDB)
    }
}
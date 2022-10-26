package mx.com.satoritech.satoritickets.repository

import kotlinx.coroutines.flow.Flow
import mx.com.satoritech.domain.models.GenericResponse
import mx.com.satoritech.domain.models.Login
import mx.com.satoritech.domain.models.User
import mx.com.satoritech.web.NetworkResult

interface AuthRepository {
    fun login(user: Login): Flow<NetworkResult<GenericResponse<User>>>
}
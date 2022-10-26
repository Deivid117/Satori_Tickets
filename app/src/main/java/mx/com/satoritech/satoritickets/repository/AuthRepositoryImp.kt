package mx.com.satoritech.satoritickets.repository

import kotlinx.coroutines.flow.Flow
import mx.com.satoritech.domain.models.GenericResponse
import mx.com.satoritech.domain.models.Login
import mx.com.satoritech.domain.models.User
import mx.com.satoritech.web.ApiService
import mx.com.satoritech.web.BaseApiResponse
import mx.com.satoritech.web.NetworkResult
import javax.inject.Inject

class AuthRepositoryImp@Inject constructor(
    val apiService: ApiService
) : AuthRepository, BaseApiResponse() {
    override fun login(user: Login): Flow<NetworkResult<GenericResponse<User>>> = safeApiCall{
        apiService.login(user)
    }
}
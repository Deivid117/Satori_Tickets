package mx.com.satoritech.satoritickets.repository

import kotlinx.coroutines.flow.Flow
import mx.com.satoritech.domain.models.ChatWith
import mx.com.satoritech.domain.models.GenericResponse
import mx.com.satoritech.domain.models.User
import mx.com.satoritech.domain.models.UserChat
import mx.com.satoritech.web.ApiService
import mx.com.satoritech.web.BaseApiResponse
import mx.com.satoritech.web.NetworkResult
import javax.inject.Inject

class ChatRepositoryImp @Inject constructor(
    val apiService: ApiService
): ChatRepository, BaseApiResponse() {

    override fun getListChats(): Flow<NetworkResult<GenericResponse<UserChat>>> = safeApiCall {
        apiService.getListChats()
    }

    override fun getChat(user_id: Int): Flow<NetworkResult<GenericResponse<ChatWith>>> = safeApiCall {
        apiService.getChat(user_id)
    }
}
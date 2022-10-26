package mx.com.satoritech.satoritickets.repository

import kotlinx.coroutines.flow.Flow
import mx.com.satoritech.domain.models.ChatWith
import mx.com.satoritech.domain.models.GenericResponse
import mx.com.satoritech.domain.models.User
import mx.com.satoritech.domain.models.UserChat
import mx.com.satoritech.web.NetworkResult

interface ChatRepository {
    fun getListChats(): Flow<NetworkResult<GenericResponse<UserChat>>>
    fun getChat(user_id: Int): Flow<NetworkResult<GenericResponse<ChatWith>>>
}
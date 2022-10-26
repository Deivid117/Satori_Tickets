package mx.com.satoritech.satoritickets.repository

import kotlinx.coroutines.flow.Flow
import mx.com.satoritech.domain.models.GenericResponse
import mx.com.satoritech.domain.models.Message
import mx.com.satoritech.web.NetworkResult

interface MessageRepository {
    fun getMessage(chat_id: Long): Flow<NetworkResult<GenericResponse<List<Message>>>>
    fun sentMessage(message: Message): Flow<NetworkResult<GenericResponse<Message>>>
}
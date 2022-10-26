package mx.com.satoritech.satoritickets.repository

import kotlinx.coroutines.flow.Flow
import mx.com.satoritech.domain.models.GenericResponse
import mx.com.satoritech.domain.models.Message
import mx.com.satoritech.web.ApiService
import mx.com.satoritech.web.BaseApiResponse
import mx.com.satoritech.web.NetworkResult
import javax.inject.Inject

class MessageRepositoryImp @Inject constructor(
    val apiService: ApiService
): MessageRepository, BaseApiResponse()  {
    override fun getMessage(chat_id: Long): Flow<NetworkResult<GenericResponse<List<Message>>>> = safeApiCall{
        apiService.getMessages(chat_id)
    }

    override fun sentMessage(message: Message): Flow<NetworkResult<GenericResponse<Message>>> = safeApiCall{
        apiService.sentMessage(message)
    }
}
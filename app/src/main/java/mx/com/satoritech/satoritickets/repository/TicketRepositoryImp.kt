package mx.com.satoritech.satoritickets.repository

import kotlinx.coroutines.flow.Flow
import mx.com.satoritech.domain.models.GenericResponse
import mx.com.satoritech.domain.models.Ticket
import mx.com.satoritech.domain.models.User
import mx.com.satoritech.web.ApiService
import mx.com.satoritech.web.BaseApiResponse
import mx.com.satoritech.web.NetworkResult
import okhttp3.MultipartBody
import javax.inject.Inject

class TicketRepositoryImp@Inject constructor(
    val apiService: ApiService
): TicketRepository, BaseApiResponse() {
    override fun getTickets(status: String): Flow<NetworkResult<GenericResponse<List<Ticket>>>> = safeApiCall {
        apiService.getTickets(status)
    }
    override fun addTicket(
        ticket: Ticket,
        pdf: MultipartBody.Part?
    ): Flow<NetworkResult<GenericResponse<Any>>> = safeApiCall {
        apiService.addTicket(ticket, pdf)
    }
    override fun getUsers(): Flow<NetworkResult<GenericResponse<List<User>>>> = safeApiCall {
        apiService.getUsers()
    }

    override fun changeStatus(
        ticket_id: Long,
        status: Int
    ): Flow<NetworkResult<GenericResponse<Any>>> = safeApiCall {
        apiService.changeStatus(ticket_id, status)
    }
}
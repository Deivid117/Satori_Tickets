package mx.com.satoritech.satoritickets.repository

import kotlinx.coroutines.flow.Flow
import mx.com.satoritech.domain.models.GenericResponse
import mx.com.satoritech.domain.models.Ticket
import mx.com.satoritech.domain.models.User
import mx.com.satoritech.web.NetworkResult
import okhttp3.MultipartBody

interface TicketRepository {
    fun getTickets(status: String): Flow<NetworkResult<GenericResponse<List<Ticket>>>>
    fun addTicket(ticket: Ticket, pdf: MultipartBody.Part?): Flow<NetworkResult<GenericResponse<Any>>>
    fun getUsers(): Flow<NetworkResult<GenericResponse<List<User>>>>
    fun changeStatus(ticket_id: Long, status: Int): Flow<NetworkResult<GenericResponse<Any>>>
}
package mx.com.satoritech.web

import mx.com.satoritech.domain.models.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST(APIConstants.wsPath + "login2")
    suspend fun login(@Body user: Login): Response<GenericResponse<User>>

    @GET(APIConstants.wsPath + "getTickets/{status}")
    suspend fun getTickets(@Path("status") status: String): Response<GenericResponse<List<Ticket>>>

    @Multipart
    @POST(APIConstants.wsPath + "addTickets")
    suspend fun addTicket(
        @Part("payload") ticket: Ticket,
        @Part pdf: MultipartBody.Part?
    ): Response<GenericResponse<Any>>

    @GET(APIConstants.wsPath + "getUsers")
    suspend fun getUsers(): Response<GenericResponse<List<User>>>

    @GET(APIConstants.wsPath + "with/{user_id}")
    suspend fun getChat(@Path("user_id") user_id: Int): Response<GenericResponse<ChatWith>>

    @GET(APIConstants.wsPath + "{chat_id}/getMessages")
    suspend fun getMessages(@Path("chat_id") chat_id: Long): Response<GenericResponse<List<Message>>>

    @POST(APIConstants.wsPath + "sentMessage")
    suspend fun sentMessage(@Body message: Message): Response<GenericResponse<Message>>

    @POST(APIConstants.wsPath + "changeStatusTicket/{ticket_id}/{status}")
    suspend fun changeStatus(
        @Path("ticket_id") ticket_id: Long,
        @Path("status") status: Int
    ): Response<GenericResponse<Any>>

    @GET(APIConstants.wsPath + "getChats")
    suspend fun getListChats(): Response<GenericResponse<UserChat>>
}
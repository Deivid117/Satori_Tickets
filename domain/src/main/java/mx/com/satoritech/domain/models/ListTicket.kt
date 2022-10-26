package mx.com.satoritech.domain.models

import com.google.gson.annotations.SerializedName

data class ListTicket(
    @SerializedName("ticket_list") var ticketList:ArrayList<Ticket> = arrayListOf()
)

package mx.com.satoritech.domain.models

import com.google.gson.annotations.SerializedName

data class UserChat(
    @SerializedName("user"     ) var user     : ArrayList<User>     = arrayListOf(),
    @SerializedName("messages" ) var messages : ArrayList<Message> = arrayListOf()
)

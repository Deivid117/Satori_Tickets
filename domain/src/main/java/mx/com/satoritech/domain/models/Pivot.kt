package mx.com.satoritech.domain.models

import com.google.gson.annotations.SerializedName

data class Pivot(
    @SerializedName("chat_id" ) var chatId : Int? = null,
    @SerializedName("user_id" ) var userId : Int? = null

)

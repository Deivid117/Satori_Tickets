package mx.com.satoritech.domain.models

import com.google.gson.annotations.SerializedName
import com.beust.klaxon.Klaxon

private val klaxon = Klaxon()

data class Message(
    @SerializedName("id"      ) var id      : Long?    = null,
    @SerializedName("content" ) var content : String? = null,
    @SerializedName("seen_message" ) var seenMessage : Int? = null,
    @SerializedName("sender_id" ) var senderId  : Int?    = null,
    @SerializedName("receiver_id" ) var receiverId  : Int?    = null,
    @SerializedName("chat_id" ) var chatId  : Int?    = null,
    @SerializedName("message_date" ) var messageDate : String? = null,
    @SerializedName("message_hour" ) var messageHour : String? = null
) {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<Message>(json)
    }
}

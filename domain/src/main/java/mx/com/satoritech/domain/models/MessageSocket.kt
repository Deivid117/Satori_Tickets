package mx.com.satoritech.domain.models

import com.beust.klaxon.Klaxon
import com.google.gson.annotations.SerializedName

private val klaxon = Klaxon()

data class MessageSocket(
    var id      : Long?    = null,
    var content : String? = null,
    var seen_message : Int? = null,
    var sender_id  : Int?    = null,
    var receiver_id  : Int?    = null,
    var chat_id  : Int?    = null,
    var message_date : String? = null,
    var message_hour : String? = null
) {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<MessageSocket>(json)
    }
}

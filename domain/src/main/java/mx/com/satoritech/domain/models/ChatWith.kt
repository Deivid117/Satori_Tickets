package mx.com.satoritech.domain.models

import com.google.gson.annotations.SerializedName

data class ChatWith(
    @SerializedName("userA" ) var userA : UserA? = UserA(),
    @SerializedName("userB" ) var userB : UserB? = UserB(),
    @SerializedName("chat"  ) var chat  : Chat?  = Chat()
)

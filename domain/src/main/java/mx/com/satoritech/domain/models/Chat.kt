package mx.com.satoritech.domain.models

import com.google.gson.annotations.SerializedName

data class Chat(
    @SerializedName("id"         ) var id        : Int?             = null,
    @SerializedName("created_at" ) var createdAt : String?          = null,
    @SerializedName("updated_at" ) var updatedAt : String?          = null,
    @SerializedName("users"      ) var users     : ArrayList<Users> = arrayListOf(),
    @SerializedName("pivot"      ) var pivot     : Pivot?  = Pivot()
)

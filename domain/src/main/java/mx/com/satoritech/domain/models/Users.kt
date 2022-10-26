package mx.com.satoritech.domain.models

import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("id"        ) var id       : Int?    = null,
    @SerializedName("name"      ) var name     : String? = null,
    @SerializedName("email"     ) var email    : String? = null,
    @SerializedName("type_user" ) var typeUser : Int?    = null,
    @SerializedName("api_token" ) var apiToken : String? = null,
    @SerializedName("pivot"     ) var pivot    : Pivot?  = Pivot()
)

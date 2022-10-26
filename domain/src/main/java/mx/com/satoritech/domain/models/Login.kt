package mx.com.satoritech.domain.models

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("email") var email: String? = null,
    @SerializedName("password") var password: String? = null,
)

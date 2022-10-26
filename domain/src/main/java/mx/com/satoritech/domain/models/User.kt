package mx.com.satoritech.domain.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "users")
data class User(
    @PrimaryKey
    @SerializedName("id") var id: Int? = null,
    @SerializedName("api_token") var apiToken: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email:String? = null,
    @SerializedName("type_user") var typeUser:Int? = null,
): Parcelable

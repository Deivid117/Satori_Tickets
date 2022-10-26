package mx.com.satoritech.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ticket(
    @SerializedName("id") var id: Long? = null,
    @SerializedName("no_ticket") var noTicket: Int? = null,
    @SerializedName("project") var project: String? = null,
    @SerializedName("team") var team: String? = null,
    @SerializedName("date") var date: String? = null,
    @SerializedName("content") var content: String? = null,
    @SerializedName("user_id") var userId: Int? = null,
    @SerializedName("creator_id") var creatorId: Int? = null,
    @SerializedName("pdf") var pdf: String? = null,
    @SerializedName("status") var status: Int? = null,
): Parcelable

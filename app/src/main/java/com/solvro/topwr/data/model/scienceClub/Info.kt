package com.solvro.topwr.data.model.scienceClub


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
data class Info(
    @Json(name = "icon") val icon: Icon?,
    @Json(name = "id") val id: Int?,
    @Json(name = "type") val type: String?,
    @Json(name = "value") val value: String?,
    @Json(name = "visibleText") val visibleText: String?
) : Parcelable {
    fun getType(): InfoType {
        return when (type) {
            "Website" -> {
                if (visibleText?.contains("facebook") == true)
                    return InfoType.FACEBOOK
                else
                    return InfoType.WEBSITE
            }
            "Email" -> InfoType.EMAIL
            else -> InfoType.WEBSITE
        }
    }

    enum class InfoType {
        WEBSITE, FACEBOOK, EMAIL
    }
}
package com.solvro.topwr.core.api.model.scienceclub


import android.os.Parcelable
import com.solvro.topwr.features.scienceclub.domain.model.Info
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class InfoRemote(
    @Json(name = "icon") val icon: IconRemote,
    @Json(name = "id") val id: String,
    @Json(name = "type") val type: String?,
    @Json(name = "value") val value: String,
    @Json(name = "visibleText") val visibleText: String?
) : Parcelable {
    fun getType(): InfoTypeRemote {
        return when (type) {
            "Website" -> {
                if (visibleText?.contains("facebook") == true)
                    return InfoTypeRemote.FACEBOOK
                else
                    return InfoTypeRemote.WEBSITE
            }
            "Email" -> InfoTypeRemote.EMAIL
            else -> InfoTypeRemote.WEBSITE
        }
    }

    fun toDomain() = Info(
        id, value, icon.toDomain(), getType().toDomain(), visibleText
    )

    enum class InfoTypeRemote {
        WEBSITE, FACEBOOK, EMAIL, PHONE_NUMBER, ADDRESS;

        fun toDomain(): Info.InfoType = when (this) {
            WEBSITE -> Info.InfoType.WEBSITE
            FACEBOOK -> Info.InfoType.FACEBOOK
            EMAIL -> Info.InfoType.EMAIL
            PHONE_NUMBER -> Info.InfoType.PHONE_NUMBER
            ADDRESS -> Info.InfoType.ADDRESS
        }
    }
}
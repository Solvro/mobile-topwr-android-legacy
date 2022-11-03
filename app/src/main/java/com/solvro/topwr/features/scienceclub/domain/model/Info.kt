package com.solvro.topwr.features.scienceclub.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Info(
    val id: String,
    val value: String,
    val icon: Icon,
    val type: InfoType?,
    val visibleText: String?
) : Parcelable {

    enum class InfoType {
        PHONE_NUMBER, ADDRESS, WEBSITE, EMAIL, FACEBOOK
    }
}

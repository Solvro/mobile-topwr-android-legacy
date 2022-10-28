package com.solvro.topwr.features.scienceclub.domain.model

data class Info(
    val id: String,
    val value: String,
    val icon: Icon,
    val type: InfoType?,
    val visibleText: String?
) {

    enum class InfoType {
        PHONE_NUMBER, ADDRESS, WEBSITE, EMAIL, FACEBOOK
    }
}

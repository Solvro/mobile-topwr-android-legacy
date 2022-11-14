package com.solvro.topwr.core.api.model.department

import com.squareup.moshi.Json

data class SocialMediaRemote(
    val Icon: IconRemote?,
    val Link: String?,
    val Name: String?,
    val id: Int?
)
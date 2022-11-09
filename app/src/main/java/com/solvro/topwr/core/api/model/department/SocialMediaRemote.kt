package com.solvro.topwr.core.api.model.department

import com.squareup.moshi.Json

data class SocialMediaRemote(
    @Json(name = "icon") val Icon: IconRemote?,
    @Json(name = "link") val Link: String?,
    @Json(name = "name") val Name: String?,
    @Json(name = "id") val id: Int?
)
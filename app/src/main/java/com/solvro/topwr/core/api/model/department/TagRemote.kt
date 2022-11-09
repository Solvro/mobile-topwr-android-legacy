package com.solvro.topwr.core.api.model.department

import com.squareup.moshi.Json

data class TagRemote(
    @Json(name = "name") val Name: String?,
    @Json(name = "id") val id: Int?
)
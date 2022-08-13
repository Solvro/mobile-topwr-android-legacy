package com.solvro.topwr.data.model.tag

import com.squareup.moshi.Json


data class TagRemote(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String
)
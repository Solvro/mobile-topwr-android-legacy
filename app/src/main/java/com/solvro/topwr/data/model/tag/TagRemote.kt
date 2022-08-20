package com.solvro.topwr.data.model.tag

import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.squareup.moshi.Json

data class TagRemote(
    @Json(name = "id") val id: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "scientific_circles") val scientific_circles: List<ScienceClub>?
)
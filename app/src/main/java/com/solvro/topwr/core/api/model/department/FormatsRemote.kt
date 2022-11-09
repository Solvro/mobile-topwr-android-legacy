package com.solvro.topwr.core.api.model.department

import com.solvro.topwr.data.model.common.Thumbnail
import com.squareup.moshi.Json

data class FormatsRemote(
    @Json(name = "small") val small: SmallRemote?,
    @Json(name = "thumbnail") val thumbnail: Thumbnail?
)
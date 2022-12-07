package com.solvro.topwr.core.api.model.department

import com.solvro.topwr.data.model.common.Thumbnail
import com.squareup.moshi.Json

data class FormatsRemote(
    val small: SmallRemote?,
    val thumbnail: Thumbnail?
)
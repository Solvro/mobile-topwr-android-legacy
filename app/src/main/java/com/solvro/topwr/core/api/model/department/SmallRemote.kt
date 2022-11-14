package com.solvro.topwr.core.api.model.department

import com.squareup.moshi.Json

data class SmallRemote(
    @field:Json(name = "ext") val ext: String?,
    @field:Json(name = "hash") val hash: String?,
    @field:Json(name = "height") val height: Int?,
    @field:Json(name = "mime") val mime: String?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "path") val path: Any?,
    @field:Json(name = "provider_metadata") val provider_metadata: ProviderMetadataRemote?,
    @field:Json(name = "size") val size: Double?,
    @field:Json(name = "url") val url: String?,
    @field:Json(name = "width") val width: Int?
)
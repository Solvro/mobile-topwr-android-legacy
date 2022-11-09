package com.solvro.topwr.core.api.model.department

import com.squareup.moshi.Json

data class SmallRemote(
    @Json(name = "ext") val ext: String?,
    @Json(name = "hash") val hash: String?,
    @Json(name = "height") val height: Int?,
    @Json(name = "mime") val mime: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "path") val path: Any?,
    @Json(name = "provider_metadata") val provider_metadata: ProviderMetadataRemote?,
    @Json(name = "size") val size: Double?,
    @Json(name = "url") val url: String?,
    @Json(name = "width") val width: Int?
)
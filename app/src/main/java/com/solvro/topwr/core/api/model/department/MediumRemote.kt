package com.solvro.topwr.core.api.model.department

import com.squareup.moshi.Json

data class MediumRemote(
    val ext: String?,
    val hash: String?,
    val height: Int?,
    val mime: String?,
    val name: String?,
    val path: Any?,
    val provider_metadata: ProviderMetadataRemote?,
    val size: Double?,
    val url: String?,
    val width: Int?
)
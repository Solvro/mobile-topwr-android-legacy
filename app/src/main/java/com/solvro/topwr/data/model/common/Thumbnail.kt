package com.solvro.topwr.data.model.common

import com.solvro.topwr.data.model.common.ProviderMetadata

data class Thumbnail(
    val ext: String?,
    val hash: String?,
    val height: Int?,
    val mime: String?,
    val name: String?,
    val path: Any?,
    val provider_metadata: ProviderMetadata?,
    val size: Double?,
    val url: String?,
    val width: Int?
)
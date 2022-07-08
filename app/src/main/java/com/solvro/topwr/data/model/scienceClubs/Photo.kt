package com.solvro.topwr.data.model.scienceClubs

import com.solvro.topwr.data.model.common.Formats
import com.solvro.topwr.data.model.common.ProviderMetadata

data class BackgroundPhoto(
    val alternativeText: String?,
    val caption: String?,
    val created_at: String?,
    val ext: String?,
    val formats: Formats?,
    val hash: String?,
    val height: Int?,
    val id: Int?,
    val mime: String?,
    val name: String?,
    val previewUrl: Any?,
    val provider: String?,
    val provider_metadata: ProviderMetadata?,
    val size: Double?,
    val updated_at: String?,
    val url: String?,
    val width: Int?
)
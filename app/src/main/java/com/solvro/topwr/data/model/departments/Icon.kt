package com.solvro.topwr.data.model.departments

import com.solvro.topwr.data.model.common.ProviderMetadata
import java.io.Serializable

data class Icon(
    val alternativeText: String?,
    val caption: String?,
    val created_at: String?,
    val ext: String?,
    val formats: Any?,
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
) : Serializable
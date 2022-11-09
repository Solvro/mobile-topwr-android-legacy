package com.solvro.topwr.core.api.model.department

import android.os.Parcelable
import com.solvro.topwr.data.model.common.Formats
import com.solvro.topwr.data.model.common.ProviderMetadata
import com.solvro.topwr.features.departments.domain.model.Logo
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class LogoRemote(
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
    val previewUrl: @RawValue Any?,
    val provider: String?,
    val provider_metadata: ProviderMetadata?,
    val size: Double?,
    val updated_at: String?,
    val url: String?,
    val width: Int?
) : Parcelable {
    fun toDomain() = Logo(
        alternativeText, caption, height, id, url, width
    )
}
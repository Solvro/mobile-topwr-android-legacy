package com.solvro.topwr.core.api.model.departments

import android.os.Parcelable
import com.solvro.topwr.data.model.common.ProviderMetadata
import com.solvro.topwr.features.departments.domain.model.Icon
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class IconRemote(
    val alternativeText: String?,
    val caption: String?,
    val created_at: String?,
    val ext: String?,
    val formats: @RawValue Any?,
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
    fun toDomain(): Icon = Icon(
       alternativeText, height, id, name, size, url, width
    )
}
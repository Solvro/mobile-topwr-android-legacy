package com.solvro.topwr.core.api.model.scienceclub

import android.os.Parcelable
import com.solvro.topwr.features.scienceclub.domain.model.Icon
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class IconRemote(
    val alternativeText: String?,
    val caption: String?,
    val created_at: String?,
    val ext: String?,
    val formats: FormatsRemote?,
    val hash: String,
    val height: Int?,
    val id: String,
    val mime: String,
    val name: String,
    val previewUrl: @RawValue Any?,
    val provider: String?,
    val provider_metadata: ProviderMetadataRemote?,
    val size: Double,
    val updated_at: String?,
    val url: String,
    val width: Int?
) : Parcelable {
    fun toDomain() = Icon(
        id,
        url
    )
}
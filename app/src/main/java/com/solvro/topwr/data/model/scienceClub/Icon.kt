package com.solvro.topwr.data.model.scienceClub

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Icon(
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
) : Parcelable
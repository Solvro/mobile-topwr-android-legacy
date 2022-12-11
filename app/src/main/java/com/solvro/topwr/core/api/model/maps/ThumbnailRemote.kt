package com.solvro.topwr.core.api.model.maps

import android.os.Parcelable
import com.solvro.topwr.data.model.common.ProviderMetadata
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Thumbnail(
    val ext: String?,
    val hash: String?,
    val height: Int?,
    val mime: String?,
    val name: String?,
    val path: @RawValue Any?,
    val provider_metadata: ProviderMetadata?,
    val size: Double?,
    val url: String?,
    val width: Int?
): Parcelable
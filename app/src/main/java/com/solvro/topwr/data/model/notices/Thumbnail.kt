package com.solvro.topwr.data.model.notices

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Thumbnail(
    val ext: String?,
    val hash: String?,
    val height: Int?,
    val mime: String?,
    val name: String?,
    val path: @RawValue Any? = null,
    val provider_metadata: ProviderMetadata?,
    val size: Double?,
    val url: String?,
    val width: Int?
): Parcelable
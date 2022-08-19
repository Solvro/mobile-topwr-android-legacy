package com.solvro.topwr.data.model.common
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Medium(
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
) : Parcelable
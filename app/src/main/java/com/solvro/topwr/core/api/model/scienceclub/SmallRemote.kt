package com.solvro.topwr.core.api.model.scienceclub

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class SmallRemote(
    val ext: String?,
    val hash: String?,
    val height: Int?,
    val mime: String?,
    val name: String?,
    val path: @RawValue Any?,
    val provider_metadata: ProviderMetadataRemote?,
    val size: Double?,
    val url: String?,
    val width: Int?
) : Parcelable
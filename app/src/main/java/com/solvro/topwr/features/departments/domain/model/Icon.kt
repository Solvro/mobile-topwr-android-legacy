package com.solvro.topwr.features.departments.domain.model

import android.os.Parcelable
import com.solvro.topwr.data.model.common.ProviderMetadata
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Icon(
    val alternativeText: String?,
    val height: Int?,
    val id: Int?,
    val name: String?,
    val size: Double?,
    val url: String?,
    val width: Int?
) : Parcelable
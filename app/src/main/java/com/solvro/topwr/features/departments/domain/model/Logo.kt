package com.solvro.topwr.features.departments.domain.model

import android.os.Parcelable
import com.solvro.topwr.data.model.common.Formats
import com.solvro.topwr.data.model.common.ProviderMetadata
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Logo(
    val alternativeText: String?,
    val caption: String?,
    val height: Int?,
    val id: Int?,
    val url: String?,
    val width: Int?
) : Parcelable
package com.solvro.topwr.data.model.scienceClub

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProviderMetadata(
    val public_id: String?,
    val resource_type: String?
) : Parcelable
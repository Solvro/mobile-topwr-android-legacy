package com.solvro.topwr.core.api.model.scienceclub

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProviderMetadataRemote(
    val public_id: String?,
    val resource_type: String?
) : Parcelable
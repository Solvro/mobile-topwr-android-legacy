package com.solvro.topwr.features.scienceclub.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BackgroundPhoto(
    val id: Int,
    val url: String?
) : Parcelable


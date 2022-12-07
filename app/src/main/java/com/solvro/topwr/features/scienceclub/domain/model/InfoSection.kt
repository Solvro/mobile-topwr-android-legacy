package com.solvro.topwr.features.scienceclub.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InfoSection(
    val id: String,
    val name: String?,
    val info: List<Info>
) : Parcelable

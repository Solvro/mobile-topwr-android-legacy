package com.solvro.topwr.features.scienceclub.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tag(
    val id: String,
    val name: String
) : Parcelable

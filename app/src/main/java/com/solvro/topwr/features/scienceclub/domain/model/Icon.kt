package com.solvro.topwr.features.scienceclub.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Icon(
    val id: String,
    val url: String
) : Parcelable

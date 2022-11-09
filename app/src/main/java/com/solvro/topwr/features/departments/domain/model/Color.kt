package com.solvro.topwr.features.departments.domain.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class Color(
    val gradientFirstValue: String,
    val gradientSecondValue: String,
    val id: Int?
) : Parcelable
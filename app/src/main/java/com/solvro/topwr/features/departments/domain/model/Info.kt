package com.solvro.topwr.features.departments.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Info(
    val icon: Icon?,
    val id: Int?,
    val type: @RawValue Any?,
    val value: String?,
): Parcelable
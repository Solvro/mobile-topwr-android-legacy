package com.solvro.topwr.data.model.departments

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.io.Serializable

@Parcelize
data class Info(
    val icon: Icon?,
    val id: Int?,
    val type: @RawValue Any?,
    val value: String?,
    val visibleText: String?
): Serializable, Parcelable
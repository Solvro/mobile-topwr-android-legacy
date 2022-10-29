package com.solvro.topwr.features.departments.domain.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class Color(
    private val gradientFirstValue: String?,
    private val gradientSecondValue: String?,
    val id: Int?
) : Parcelable {
    @IgnoredOnParcel
    val gradientFirst: String? = gradientFirstValue
        get() = field?.let { "#$it" } ?: "#$PLACEHOLDER_COLOR"
    @IgnoredOnParcel
    val gradientSecond: String? = gradientSecondValue
        get() = field?.let { "#$it" } ?: "#$PLACEHOLDER_COLOR"

    companion object {
        const val PLACEHOLDER_COLOR = "DB2B10"
    }
}
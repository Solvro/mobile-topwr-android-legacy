package com.solvro.topwr.core.api.model.department

import android.os.Parcelable
import com.solvro.topwr.features.departments.domain.model.Color
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class ColorRemote(
    private val gradientFirstValue: String?,
    private val gradientSecondValue: String?,
    val id: Int?
) : Parcelable {
    fun toDomain() = Color(
        gradientFirstValue,
        gradientSecondValue,
        id
    )

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
package com.solvro.topwr.core.api.model.department

import android.os.Parcelable
import com.solvro.topwr.features.departments.domain.model.Color
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
class ColorRemote(
    @Json(name = "id") val id: Int?,
    @Json(name = "gradientFirst") private val gradientFirstValue: String?,
    @Json(name = "gradientSecond") private val gradientSecondValue: String?
) : Parcelable {

    fun toDomain() = Color(
        gradientFirstValue?.let { "#$it" } ?: "#$PLACEHOLDER_COLOR",
        gradientSecondValue?.let { "#$it" } ?: "#$PLACEHOLDER_COLOR",
        id
    )

    companion object {
        const val PLACEHOLDER_COLOR = "DB2B10"
    }
}
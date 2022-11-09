package com.solvro.topwr.core.api.model.department

import android.os.Parcelable
import com.solvro.topwr.features.departments.domain.model.Info
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class InfoRemote(
    @Json(name = "icon") val icon: IconRemote?,
    @Json(name = "id") val id: Int?,
    @Json(name = "type") val type: @RawValue Any?,
    @Json(name = "value") val value: String?,
    @Json(name = "visibleText") val visibleText: String?
): Parcelable {
    fun toDomain() = Info(
        icon?.toDomain(),
        id,
        type, value
    )
}
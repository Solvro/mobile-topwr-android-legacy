package com.solvro.topwr.core.api.model.department

import android.os.Parcelable
import com.solvro.topwr.features.departments.domain.model.Info
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class InfoRemote(
    val icon: IconRemote?,
    val id: Int?,
    val type: @RawValue Any?,
    val value: String?,
    val visibleText: String?
) : Parcelable {
    fun toDomain() = Info(
        icon?.toDomain(),
        id,
        type, value
    )
}
package com.solvro.topwr.core.api.model.departments

import android.os.Parcelable
import com.solvro.topwr.features.departments.domain.model.Info
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class InfoRemote(
    val icon: IconRemote?,
    val id: Int?,
    val type: @RawValue Any?,
    val value: String?,
    val visibleText: String?
): Parcelable {
    fun toDomain() = Info(
        icon?.toDomain(),
        id,
        type, value
    )
}
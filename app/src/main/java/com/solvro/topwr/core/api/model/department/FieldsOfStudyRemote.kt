package com.solvro.topwr.core.api.model.department

import android.os.Parcelable
import com.solvro.topwr.features.departments.domain.model.FieldsOfStudy
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class FieldsOfStudyRemote(
    val id: Int?,
    val name: String?
) : Parcelable {
    fun toDomain() = FieldsOfStudy(
        id, name
    )
}
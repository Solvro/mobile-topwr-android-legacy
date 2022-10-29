package com.solvro.topwr.core.api.model.departments

import android.os.Parcelable
import com.solvro.topwr.features.departments.domain.model.FieldsOfStudy
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
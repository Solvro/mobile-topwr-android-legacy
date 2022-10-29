package com.solvro.topwr.features.departments.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FieldsOfStudy(
    val id: Int?,
    val name: String?
) : Parcelable
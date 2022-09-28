package com.solvro.topwr.data.model.departments

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FieldsOfStudy(
    val id: Int?,
    val name: String?
) : Parcelable
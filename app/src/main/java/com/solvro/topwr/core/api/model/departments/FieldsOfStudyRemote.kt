package com.solvro.topwr.core.api.model.departments

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FieldsOfStudyRemote(
    val id: Int?,
    val name: String?
) : Parcelable
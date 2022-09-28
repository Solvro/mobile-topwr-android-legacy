package com.solvro.topwr.data.model.departments

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val Address: String?,
    val id: Int?
): Parcelable
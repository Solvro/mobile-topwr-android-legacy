package com.solvro.topwr.core.api.model.department

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactRemote(
    val Name: String?,
    val Number: String?,
    val id: Int?
) : Parcelable
package com.solvro.topwr.data.model.notices

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val Name: String?,
    val Number: String?,
    val id: Int?
): Parcelable
package com.solvro.topwr.core.api.model.scienceclub

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactRemote(
    val Name: String?,
    val Number: String?,
    val id: Int?
) : Parcelable
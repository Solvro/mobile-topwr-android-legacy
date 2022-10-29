package com.solvro.topwr.core.api.model.departments

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressRemote(
    val Address: String?,
    val id: Int?
): Parcelable
package com.solvro.topwr.core.api.model.department

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressRemote(
    @Json(name = "address") val Address: String?,
    @Json(name = "id") val id: Int?
): Parcelable
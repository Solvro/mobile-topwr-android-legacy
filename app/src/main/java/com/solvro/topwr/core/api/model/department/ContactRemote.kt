package com.solvro.topwr.core.api.model.department

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactRemote(
    @Json(name = "name") val Name: String?,
    @Json(name = "number") val Number: String?,
    @Json(name = "id") val id: Int?
) : Parcelable
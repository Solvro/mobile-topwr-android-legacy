package com.solvro.topwr.data.model.scienceClub


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
data class InfoSection(
    @Json(name = "id") val id: Int,
    @Json(name = "info") val info: List<Info>,
    @Json(name = "name") val name: String
) : Parcelable
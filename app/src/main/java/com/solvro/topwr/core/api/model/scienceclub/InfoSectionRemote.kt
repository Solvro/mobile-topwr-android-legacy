package com.solvro.topwr.core.api.model.scienceclub


import android.os.Parcelable
import com.solvro.topwr.features.scienceclub.domain.model.InfoSection
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class InfoSectionRemote(
    @Json(name = "id") val id: String,
    @Json(name = "info") val info: List<InfoRemote>,
    @Json(name = "name") val name: String
) : Parcelable {
    fun toDomain() = InfoSection(
        id, name, info.map { it.toDomain() }
    )
}
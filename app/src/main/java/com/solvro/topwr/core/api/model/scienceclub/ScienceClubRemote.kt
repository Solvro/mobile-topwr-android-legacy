package com.solvro.topwr.core.api.model.scienceclub

import android.os.Parcelable
import com.solvro.topwr.data.model.common.Photo
import com.solvro.topwr.features.scienceclub.domain.model.ScienceClub
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ScienceClubRemote(
    @Json(name = "backgroundPhoto") val backgroundPhoto: BackgroundPhotoRemote?,
    @Json(name = "created_at") val created_at: String?,
    @Json(name = "department") val department: Int?,
    @Json(name = "description") val description: String,
    @Json(name = "id") val id: Int,
    @Json(name = "infoSection") val infoSection: List<InfoSectionRemote>?,
    @Json(name = "locale") val locale: String?,
    @Json(name = "localizations") val localizations: @RawValue List<Any>?,
    @Json(name = "name") val name: String,
    @Json(name = "photo") val photo: Photo?,
    @Json(name = "published_at") val published_at: String?,
    @Json(name = "tags") val tags: List<TagRemote>?,
    @Json(name = "updated_at") val updated_at: String?
) : Parcelable {
    fun toDomain() = ScienceClub(
        backgroundPhoto = backgroundPhoto?.toDomain(),
        departmentNumber = department,
        description = description,
        id = id,
        infoSection = infoSection?.map { it.toDomain() },
        name = name,
        photo = photo,
        tags = tags?.map { it.toDomain() },
    )
}
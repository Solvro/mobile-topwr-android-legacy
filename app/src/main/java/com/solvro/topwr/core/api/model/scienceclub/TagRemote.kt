package com.solvro.topwr.core.api.model.scienceclub

import android.os.Parcelable
import com.solvro.topwr.features.scienceclub.domain.model.Tag
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class TagRemote(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String?
) : Parcelable {
    fun toDomain(): Tag = Tag(
        id = id,
        name = name ?: ""
    )
}
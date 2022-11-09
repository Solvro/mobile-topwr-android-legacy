package com.solvro.topwr.core.api.model.department

import android.os.Parcelable
import com.solvro.topwr.data.model.common.Formats
import com.solvro.topwr.data.model.common.ProviderMetadata
import com.solvro.topwr.features.departments.domain.model.Logo
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class LogoRemote(
    @Json(name = "alternativeText") val alternativeText: String?,
    @Json(name = "caption") val caption: String?,
    @Json(name = "created_at") val created_at: String?,
    @Json(name = "ext") val ext: String?,
    @Json(name = "formats") val formats: Formats?,
    @Json(name = "hash") val hash: String?,
    @Json(name = "height") val height: Int?,
    @Json(name = "id") val id: Int?,
    @Json(name = "mime") val mime: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "previewUrl") val previewUrl: @RawValue Any?,
    @Json(name = "provider") val provider: String?,
    @Json(name = "provider_metadata") val provider_metadata: ProviderMetadata?,
    @Json(name = "size") val size: Double?,
    @Json(name = "updated_at") val updated_at: String?,
    @Json(name = "url") val url: String?,
    @Json(name = "width") val width: Int?
) : Parcelable {
    fun toDomain() = Logo(
        alternativeText, caption, height, id, url, width
    )
}
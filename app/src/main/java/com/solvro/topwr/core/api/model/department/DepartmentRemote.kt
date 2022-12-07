package com.solvro.topwr.core.api.model.department

import android.os.Parcelable
import com.solvro.topwr.features.departments.domain.model.Department
import com.squareup.moshi.Json
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
class DepartmentRemote(
    @Json(name = "addres") val addres: String?,
    @Json(name = "code") val code: String?,
    @Json(name = "color") private val colorValue: ColorRemote?,
    @Json(name = "created_at") val created_at: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "displayOrder") val displayOrder: Int?,
    @Json(name = "fieldsOfStudy") val fieldsOfStudy: List<FieldsOfStudyRemote>?,
    @Json(name = "id") val id: Int?,
    @Json(name = "infoSection") val infoSection: List<InfoSectionRemote>?,
    @Json(name = "latitude") val latitude: Double?,
    @Json(name = "locale") val locale: String?,
    @Json(name = "localizations") val localizations: @RawValue List<Any>?,
    @Json(name = "logo") val logo: LogoRemote?,
    @Json(name = "longitude") val longitude: Double?,
    @Json(name = "name") val name: String?,
    @Json(name = "published_at") val published_at: String?,
    @Json(name = "scientificCircles") val scientificCircles: List<Int>?,
    @Json(name = "updated_at") val updated_at: String?,
    @Json(name = "website") val website: String?
) : Parcelable {

    fun toDomain() = Department(
        addres,
        code,
        colorRemote!!.toDomain(),
        description,
        displayOrder,
        fieldsOfStudy?.map { it.toDomain() },
        id,
        infoSection?.map { it.toDomain() },
        latitude,
        logo?.toDomain(),
        longitude,
        name
    )

    @IgnoredOnParcel
    val colorRemote: ColorRemote? = colorValue
        get() = field ?: ColorRemote(
            id,
            ColorRemote.PLACEHOLDER_COLOR,
            ColorRemote.PLACEHOLDER_COLOR
        )
}
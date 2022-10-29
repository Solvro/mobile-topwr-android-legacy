package com.solvro.topwr.core.api.model.departments

import android.os.Parcelable
import com.solvro.topwr.features.departments.domain.model.Departments
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
class DepartmentsRemote(
    val addres: String?,
    val code: String?,
    private val colorValue: ColorRemote?,
    val created_at: String?,
    val description: String?,
    val displayOrder: Int?,
    val fieldsOfStudy: List<FieldsOfStudyRemote>?,
    val id: Int?,
    val infoSection: List<InfoSectionRemote>?,
    val latitude: Double?,
    val locale: String?,
    val localizations: @RawValue List<Any>?,
    val logo: LogoRemote?,
    val longitude: Double?,
    val name: String?,
    val published_at: String?,
    val scientificCircles: List<Int>?,
    val updated_at: String?,
    val website: String?
) : Parcelable {

    fun toDomain() = Departments(
        addres,
        code,
        colorValue?.toDomain(),
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
        get() = field ?: ColorRemote(ColorRemote.PLACEHOLDER_COLOR, ColorRemote.PLACEHOLDER_COLOR, id)
}
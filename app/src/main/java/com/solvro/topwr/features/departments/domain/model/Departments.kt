package com.solvro.topwr.features.departments.domain.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
class Departments(
    val addres: String?,
    val code: String?,
    private val colorValue: Color?,
    val description: String?,
    val displayOrder: Int?,
    val fieldsOfStudy: List<FieldsOfStudy>?,
    val id: Int?,
    val infoSection: List<InfoSection>?,
    val latitude: Double?,
    val logo: Logo?,
    val longitude: Double?,
    val name: String?,
) : Parcelable {

    @IgnoredOnParcel
    val color: Color? = colorValue
        get() = field ?: Color(Color.PLACEHOLDER_COLOR, Color.PLACEHOLDER_COLOR, id)
}
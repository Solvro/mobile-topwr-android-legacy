package com.solvro.topwr.data.model.departments

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
class Departments(
    val addres: String?,
    val code: String?,
    private val colorValue: Color?,
    val created_at: String?,
    val description: String?,
    val displayOrder: Int?,
    val fieldsOfStudy: List<FieldsOfStudy>?,
    val id: Int?,
    val infoSection: List<InfoSection>?,
    val latitude: Double?,
    val locale: String?,
    val localizations: @RawValue List<Any>?,
    val logo: Logo?,
    val longitude: Double?,
    val name: String?,
    val published_at: String?,
    val scientificCircles: List<Int>?,
    val updated_at: String?,
    val website: String?
) : Parcelable {

    @IgnoredOnParcel
    val color: Color? = colorValue
        get() = field ?: Color(Color.PLACEHOLDER_COLOR, Color.PLACEHOLDER_COLOR, id)
}
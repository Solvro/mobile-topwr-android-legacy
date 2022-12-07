package com.solvro.topwr.features.departments.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Department(
    val addres: String?,
    val code: String?,
    val color: Color,
    val description: String?,
    val displayOrder: Int?,
    val fieldsOfStudy: List<FieldsOfStudy>?,
    val id: Int?,
    val infoSection: List<InfoSection>?,
    val latitude: Double?,
    val logo: Logo?,
    val longitude: Double?,
    val name: String?,
) : Parcelable
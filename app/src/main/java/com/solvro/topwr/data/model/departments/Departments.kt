package com.solvro.topwr.data.model.departments

data class Departments(
    val addres: String?,
    val code: String?,
    val color: Color?,
    val created_at: String?,
    val description: String?,
    val fieldsOfStudy: List<FieldsOfStudy>?,
    val id: Int?,
    val infoSection: List<InfoSection>?,
    val latitude: Double?,
    val locale: String?,
    val localizations: List<Any>?,
    val logo: Logo?,
    val longitude: Double?,
    val name: String?,
    val published_at: String?,
    val scientificCircles: List<Int>?,
    val updated_at: String?,
    val website: String?
)
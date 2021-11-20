package com.solvro.topwr.data.model.departments

data class Departments(
    val Address: Address?,
    val Code: String?,
    val Description: String?,
    val FieldOfStudy: List<FieldOfStudy>?,
    val Logo: Logo?,
    val Name: String?,
    val Photo: Photo?,
    val SocialMedia: List<SocialMedia>?,
    val Website: String?,
    val created_at: String?,
    val id: Int?,
    val locale: String?,
    val localizations: List<Any>?,
    val published_at: String?,
    val scientific_circles: List<ScientificCircle>?,
    val updated_at: String?
)
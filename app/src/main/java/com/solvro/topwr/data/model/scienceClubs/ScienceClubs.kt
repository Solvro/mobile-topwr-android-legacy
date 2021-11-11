package com.solvro.topwr.data.model.scienceClubs

import com.solvro.topwr.data.model.departments.Departments

data class ScienceClubs(
    val Contact: List<Contact>?,
    val Description: String?,
    val Name: String?,
    val Photo: Photo?,
    val SocialMedia: List<SocialMedia>?,
    val Tag: List<Tag>?,
    val created_at: String?,
    val department: Departments?,
    val id: Int?,
    val locale: String?,
    val localizations: List<Any>?,
    val published_at: String?,
    val updated_at: String?
)
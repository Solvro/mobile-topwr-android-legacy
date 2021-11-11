package com.solvro.topwr.data.model.departments

data class ScientificCircle(
    val Contact: List<Contact>?,
    val Description: String?,
    val Name: String?,
    val Photo: Photo?,
    val SocialMedia: List<SocialMedia>?,
    val Tag: List<Tag>?,
    val created_at: String?,
    val department: Int?,
    val id: Int?,
    val locale: String?,
    val published_at: String?,
    val updated_at: String?
)
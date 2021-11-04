package com.solvro.topwr.data.model.notices

data class Notices(
    val Contact: Contact?,
    val Description: String?,
    val Photo: Photo?,
    val SocialMedia: List<SocialMedia>?,
    val Title: String?,
    val created_at: String?,
    val id: Int?,
    val locale: String?,
    val localizations: List<Any>?,
    val published_at: String?,
    val updated_at: String?
)
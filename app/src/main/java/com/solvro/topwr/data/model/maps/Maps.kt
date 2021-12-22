package com.solvro.topwr.data.model.maps

data class Maps(
    val Address: Address?,
    val Code: String?,
    val Description: String?,
    val Latitude: Double?,
    val Longitude: Double?,
    val Name: String?,
    val Photo: Photo?,
    val SocialMedia: List<Any>?,
    val created_at: String?,
    val id: Int?,
    val locale: String?,
    val localizations: List<Any>?,
    val published_at: String?,
    val updated_at: String?
)
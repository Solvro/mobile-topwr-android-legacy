package com.solvro.topwr.data.model.maps

import com.solvro.topwr.data.model.common.Photo

data class Building(
    val addres: String?,
    val code: String?,
    val created_at: String?,
    val description: String?,
    val id: Int?,
    val infoSection: List<Any>?,
    val latitude: Double?,
    val locale: String?,
    val localizations: List<Any>?,
    val longitude: Double?,
    val name: String?,
    val photo: Photo?,
    val published_at: String?,
    val updated_at: String?
)
package com.solvro.topwr.core.api.model.departments

import com.solvro.topwr.data.model.common.Photo

data class ScientificCircleRemote(
    val Contact: List<ContactRemote>?,
    val Description: String?,
    val Name: String?,
    val Photo: Photo?,
    val SocialMedia: List<SocialMediaRemote>?,
    val Tag: List<TagRemote>?,
    val created_at: String?,
    val department: Int?,
    val id: Int?,
    val locale: String?,
    val published_at: String?,
    val updated_at: String?
)
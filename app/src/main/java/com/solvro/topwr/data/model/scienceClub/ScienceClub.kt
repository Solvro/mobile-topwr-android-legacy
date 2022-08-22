package com.solvro.topwr.data.model.scienceClub

import com.solvro.topwr.data.model.common.Photo
import com.squareup.moshi.JsonClass

data class ScienceClub(
    val backgroundPhoto: BackgroundPhoto?,
    val created_at: String?,
    val department: Int?,
    val description: String?,
    val id: Int?,
    val infoSection: List<Any>?,
    val locale: String?,
    val localizations: List<Any>?,
    val name: String?,
    val photo: Photo?,
    val published_at: String?,
    val tags: List<Tag>?,
    val updated_at: String?
) {
    fun isTaggedAs(tagName: String) = tags?.any { tag -> tag.name == tagName } ?: false
}
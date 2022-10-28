package com.solvro.topwr.features.scienceclub.domain.model

import com.solvro.topwr.data.model.common.Photo

data class ScienceClub(
    val backgroundPhoto: BackgroundPhoto?,
    val departmentNumber: Int?,
    val description: String,
    val id: Int,
    val infoSection: List<InfoSection>?,
    val name: String,
    val photo: Photo?,
    val tags: List<Tag>?,
) {
    fun isTaggedAs(tagName: String) = tags?.any { tag -> tag.name == tagName } ?: false
}
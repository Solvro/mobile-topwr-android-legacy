package com.solvro.topwr.features.scienceclub.domain.model

import android.os.Parcelable
import com.solvro.topwr.data.model.common.Photo
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScienceClub(
    val backgroundPhoto: BackgroundPhoto?,
    val departmentNumber: Int?,
    val description: String,
    val id: Int,
    val infoSection: List<InfoSection>?,
    val name: String,
    val photo: Photo?,
    val tags: List<Tag>?,
) : Parcelable {
    fun isTaggedAs(tagName: String) = tags?.any { tag -> tag.name == tagName } ?: false
}
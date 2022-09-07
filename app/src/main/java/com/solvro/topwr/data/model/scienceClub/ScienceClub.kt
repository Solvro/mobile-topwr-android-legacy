package com.solvro.topwr.data.model.scienceClub

import android.os.Parcelable
import com.solvro.topwr.data.model.common.Photo
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ScienceClub(
    val backgroundPhoto: BackgroundPhoto?,
    val created_at: String?,
    val department: Int?,
    val description: String?,
    val id: Int?,
    val infoSection: List<InfoSection>?,
    val locale: String?,
    val localizations: @RawValue List<Any>?,
    val name: String?,
    val photo: Photo?,
    val published_at: String?,
    val tags: List<Tag>?,
    val updated_at: String?
) : Parcelable {
    fun isTaggedAs(tagName: String) = tags?.any { tag -> tag.name == tagName } ?: false
}
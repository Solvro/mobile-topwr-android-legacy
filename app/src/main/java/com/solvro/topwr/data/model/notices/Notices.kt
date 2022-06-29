package com.solvro.topwr.data.model.notices

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Notices(
    val Contact: Contact?,
    val Description: String?,
    val Photo: Photo?,
    val SocialMedia: List<SocialMedia>?,
    val Title: String?,
    val created_at: String?,
    val id: Int?,
    val locale: String?,
    val localizations: @RawValue List<Any>? = null,
    val published_at: String?,
    val updated_at: String?
) : Parcelable
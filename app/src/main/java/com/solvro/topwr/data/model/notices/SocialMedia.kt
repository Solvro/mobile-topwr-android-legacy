package com.solvro.topwr.data.model.notices

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class SocialMedia(
    val Icon: @RawValue Any? = null,
    val Link: String?,
    val Name: String?,
    val id: Int?
): Parcelable
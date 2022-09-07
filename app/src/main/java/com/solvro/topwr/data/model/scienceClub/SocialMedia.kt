package com.solvro.topwr.data.model.scienceClub

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SocialMedia(
    val Icon: Icon?,
    val Link: String?,
    val Name: String?,
    val id: Int?
) : Parcelable
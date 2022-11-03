package com.solvro.topwr.core.api.model.scienceclub

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SocialMediaRemote(
    val Icon: IconRemote?,
    val Link: String?,
    val Name: String?,
    val id: Int?
) : Parcelable
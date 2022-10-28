package com.solvro.topwr.core.api.model.scienceclub

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FormatsRemote(
    val large: LargeRemote?,
    val medium: MediumRemote?,
    val small: SmallRemote?,
    val thumbnail: ThumbnailRemote?
) : Parcelable
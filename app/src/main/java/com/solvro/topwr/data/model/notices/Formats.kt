package com.solvro.topwr.data.model.notices

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Formats(
    val large: Large?,
    val medium: Medium?,
    val small: Small?,
    val thumbnail: Thumbnail?
): Parcelable
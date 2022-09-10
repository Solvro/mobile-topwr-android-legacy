package com.solvro.topwr.data.model.aboutUs

import android.os.Parcelable
import com.solvro.topwr.data.model.common.Photo
import kotlinx.parcelize.Parcelize

@Parcelize
data class AboutUs (
    val id: String?,
    val content: String?,
    val photo: Photo?
): Parcelable
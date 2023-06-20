package com.solvro.topwr.features.faq.domain.model

import android.os.Parcelable
import com.solvro.topwr.data.model.common.Photo
import kotlinx.parcelize.Parcelize

@Parcelize
data class AboutUs (
    val id: String?,
    val content: String?,
    val photo: Photo?
): Parcelable
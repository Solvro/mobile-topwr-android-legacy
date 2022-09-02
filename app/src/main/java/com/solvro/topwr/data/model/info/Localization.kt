package com.solvro.topwr.data.model.info

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Localization (
    val id: String?,
    val title: String?,
    val shortDescription: String?,
    val photo: String?,
    val infoSection: String?,
    val description: String?,
    val localizations: String?,
    val locale: String?,
    val published_at: String?,
    val created_by: String?,
    val updated_by: String?,
): Parcelable
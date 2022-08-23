package com.solvro.topwr.data.model.info

import android.os.Parcelable
import com.solvro.topwr.data.model.common.Photo
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Info(
    val created_at: String?,
    val description: String?,
    val id: Int?,
    val infoSection: @RawValue List<Any>?,
    val locale: String?,
    val localizations: @RawValue List<Any>?,
    val photo: Photo?,
    val published_at: String?,
    val shortDescription: String?,
    val title: String?,
    val updated_at: String?
) : Parcelable
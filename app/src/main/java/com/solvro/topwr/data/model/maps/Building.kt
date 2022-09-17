package com.solvro.topwr.data.model.maps

import android.os.Parcelable
import com.solvro.topwr.data.model.common.Photo
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Building(
    val addres: String?,
    val code: String?,
    val created_at: String?,
    val description: String?,
    val id: Int?,
    val infoSection: @RawValue List<Any>?,
    val latitude: Double?,
    val locale: String?,
    val localizations: @RawValue List<Any>?,
    val longitude: Double?,
    val name: String?,
    val photo: Photo?,
    val published_at: String?,
    val updated_at: String?
) : Parcelable
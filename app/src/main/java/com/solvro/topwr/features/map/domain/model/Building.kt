package com.solvro.topwr.features.map.domain.model

import android.os.Parcelable
import android.provider.ContactsContract.DisplayPhoto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Building (
    val address: String?,
    val code: String?,
    val id: Int?,
    val name: String?,
    val photo_url: String?,
    val longitude: Double?,
    val latitude: Double?,
): Parcelable
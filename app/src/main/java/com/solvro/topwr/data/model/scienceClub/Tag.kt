package com.solvro.topwr.data.model.scienceClub

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tag(
    val name: String?,
    val id: Int?
) : Parcelable
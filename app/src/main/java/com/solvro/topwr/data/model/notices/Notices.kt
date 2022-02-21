package com.solvro.topwr.data.model.notices

import com.solvro.topwr.data.model.common.Photo

data class Notices(
    val created_at: String?,
    val description: String?,
    val id: Int?,
    val infoSection: List<Any>?,
    val link: Any?,
    val locale: String?,
    val localizations: List<Any>?,
    val photo: Photo?,
    val published_at: String?,
    val title: String?,
    val updated_at: String?
)
package com.solvro.topwr.data.model.info

data class Information(
    val id: String,
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val contact: String?,
    val socialMedia: String?
)

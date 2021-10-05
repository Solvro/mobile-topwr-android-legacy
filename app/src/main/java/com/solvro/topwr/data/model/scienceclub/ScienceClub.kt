package com.solvro.topwr.data.model.scienceclub

data class ScienceClub(
    val id: String,
    val name: String,
    val faculty: String,
    val description: String,
    val contact: String,
    val socialMedia: String,
    val tags: List<String>
)

package com.solvro.topwr.data.model.department

data class Department(
    val id: String,
    val name: String,
    val code: String,
    val majors: List<String>,
    val address: String,
    val websiteUrl: String,
    val socialMediaUrl: String,
    val immageUrl: String
)

package com.solvro.topwr.data.model.departments

class Color(
    gradientFirst: String?,
    gradientSecond: String?,
    val id: Int?
) {
    val gradientFirst: String? = gradientFirst
        get() = "#$field"
    val gradientSecond: String? = gradientSecond
        get() = "#$field"
}
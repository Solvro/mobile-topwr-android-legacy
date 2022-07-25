package com.solvro.topwr.data.model.departments

import java.io.Serializable

class Color(
    gradientFirst: String?,
    gradientSecond: String?,
    val id: Int?
) : Serializable {

    val gradientFirst: String
        get() = "#$field"
    val gradientSecond: String
        get() = "#$field"

    init {
        this.gradientFirst = gradientFirst ?: ""
        this.gradientSecond = gradientSecond ?: ""
    }
}
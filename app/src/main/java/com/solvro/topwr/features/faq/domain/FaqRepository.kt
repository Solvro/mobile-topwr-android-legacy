package com.solvro.topwr.features.faq.domain

import com.solvro.topwr.core.domain.model.Resource
import com.solvro.topwr.features.faq.domain.model.AboutUs
import com.solvro.topwr.features.faq.domain.model.Info

interface FaqRepository {
    suspend fun getInfos(nameFilter: String): Resource<List<Info>>
    suspend fun getAboutUs(): Resource<AboutUs>
}
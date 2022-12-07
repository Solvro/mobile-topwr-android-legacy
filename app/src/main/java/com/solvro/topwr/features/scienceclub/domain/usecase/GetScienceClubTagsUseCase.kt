package com.solvro.topwr.features.scienceclub.domain.usecase

import com.solvro.topwr.core.domain.model.Resource
import com.solvro.topwr.core.base.BaseUseCase
import com.solvro.topwr.features.scienceclub.domain.ScienceClubRepository
import javax.inject.Inject

class GetScienceClubTagsUseCase @Inject constructor(
    private val scienceClubRepository: ScienceClubRepository
) : BaseUseCase<Unit, Resource<List<String>>>() {
    override suspend fun action(params: Unit): Resource<List<String>> {
        return scienceClubRepository.getScienceClubTags().map { list ->
            list.map { it.name }.sorted()
        }
    }
}
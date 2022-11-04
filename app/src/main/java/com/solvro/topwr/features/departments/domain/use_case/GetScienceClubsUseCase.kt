package com.solvro.topwr.features.departments.domain.use_case

import androidx.paging.PagingData
import com.solvro.topwr.core.base.FlowUseCase
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.features.departments.domain.DepartmentsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScienceClubsUseCase @Inject constructor(
    private val departmentsRepository: DepartmentsRepository
) : FlowUseCase<Unit, PagingData<ScienceClub>>() {

    override suspend fun action(params: Unit): Flow<PagingData<ScienceClub>> {
        return departmentsRepository.getScienceClubsPaged()
    }
}
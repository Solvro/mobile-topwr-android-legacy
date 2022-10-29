package com.solvro.topwr.features.departments.domain.use_case

import androidx.paging.PagingData
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.features.departments.domain.DepartmentsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScienceClubs_UseCase @Inject constructor(
    private val departmentsRepository: DepartmentsRepository
) {
    operator fun invoke(): Flow<PagingData<ScienceClub>> {
        return departmentsRepository.getScienceClubsPaged()
    }
}
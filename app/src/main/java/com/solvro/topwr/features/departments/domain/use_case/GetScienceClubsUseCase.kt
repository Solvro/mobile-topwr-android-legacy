package com.solvro.topwr.features.departments.domain.use_case

import androidx.paging.PagingData
import androidx.paging.filter
import com.solvro.topwr.core.base.FlowUseCase
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.features.departments.domain.DepartmentsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetScienceClubsUseCase @Inject constructor(
    private val departmentsRepository: DepartmentsRepository
) : FlowUseCase<GetScienceClubsDetailsParams, PagingData<ScienceClub>>() {

    private var getScienceClubsJob: Job? = null

    override suspend fun action(params: GetScienceClubsDetailsParams): Flow<PagingData<ScienceClub>> {
        getScienceClubsJob?.cancel()
        return departmentsRepository.getScienceClubsPaged()
            .cancellable()
            .transform {
                val filteredData = it.filter { scienceClub ->
                    (scienceClub.department == params.displayOrder)
                }

                emit(filteredData)
            }
    }
}

data class GetScienceClubsDetailsParams(
    val displayOrder: Int?
)
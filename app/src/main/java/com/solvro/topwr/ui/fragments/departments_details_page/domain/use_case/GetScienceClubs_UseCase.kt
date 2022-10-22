package com.solvro.topwr.ui.fragments.departments_details_page.domain.use_case

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.ui.fragments.departments_details_page.domain.repository.DepartmentDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScienceClubs_UseCase @Inject constructor(
    private val departmentDetailsRepository: DepartmentDetailsRepository
) {
    operator fun invoke(): Flow<PagingData<ScienceClub>> {
        return departmentDetailsRepository.getScienceClubsPaged()
    }
}
package com.solvro.topwr.ui.fragments.departments_page.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DepartmentRepository {
    suspend fun getDepartment(departmentNumber: Int): Resource<Departments?>
    fun getDepartmentsPaged(): Flow<PagingData<Departments>>
//
//    fun getDepartments(): Flow<PagingData<Departments>>
}
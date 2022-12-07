package com.solvro.topwr.features.scienceclub.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.solvro.topwr.core.domain.model.Resource
import com.solvro.topwr.features.scienceclub.domain.model.ScienceClub
import com.solvro.topwr.features.scienceclub.domain.usecase.GetAllScienceClubsParams
import com.solvro.topwr.features.scienceclub.domain.usecase.GetAllScienceClubsUseCase
import com.solvro.topwr.features.scienceclub.domain.usecase.GetScienceClubTagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScienceClubsViewModel @Inject constructor(
    private val getScienceClubTagsUseCase: GetScienceClubTagsUseCase,
    private val getAllScienceClubsUseCase: GetAllScienceClubsUseCase
) : ViewModel() {

    private var lastTextFilter: String = ""

    /** LiveData */
    private val _scienceClubs by lazy { MutableLiveData<PagingData<ScienceClub>>() }
    val scienceClubs: LiveData<PagingData<ScienceClub>> by lazy { _scienceClubs }

    private val _scienceClubTags by lazy { MutableLiveData<List<String>>() }
    val scienceClubTags: LiveData<List<String>> by lazy { _scienceClubTags }

    private val _selectedCategory by lazy { MutableLiveData<String>(null) }
    val selectedCategory: LiveData<String> by lazy { _selectedCategory }

    init {
        getScienceClubs()
        getScienceClubTags()
    }

    fun setTextFilter(text: String) {
        lastTextFilter = text
        getScienceClubs(
            tagFilter = _selectedCategory.value,
            textFilter = text
        )
    }

    fun setCategoriesFilter(tagFilter: String?) {
        _selectedCategory.postValue(
            tagFilter
        )
        getScienceClubs(
            tagFilter = tagFilter,
            textFilter = lastTextFilter,
        )
    }

    private fun getScienceClubs(tagFilter: String? = null, textFilter: String = "") {
        getAllScienceClubsUseCase(
            GetAllScienceClubsParams(tagFilter, textFilter),
            scope = viewModelScope
        ) {
            _scienceClubs.postValue(it)
        }
    }

    private fun getScienceClubTags() {
        getScienceClubTagsUseCase(
            Unit,
            scope = viewModelScope
        ) { result ->
            if (result is Resource.Success) {
                _scienceClubTags.postValue(result.data)
            }
        }
    }
}
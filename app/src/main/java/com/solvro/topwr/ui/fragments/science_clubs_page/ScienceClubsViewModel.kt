package com.solvro.topwr.ui.fragments.science_clubs_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.data.repository.MainRepository
import com.solvro.topwr.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScienceClubsViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _scienceClubs by lazy {
        MutableLiveData<PagingData<ScienceClub>>()
    }
    val scienceClubs: LiveData<PagingData<ScienceClub>> by lazy {
        _scienceClubs
    }

    init {
        getScienceClubs()
    }

    private val _categoriesState by lazy {
        MutableLiveData<CategoriesState>()
            .also { getScienceClubTags(it) }
    }
    val categoriesState: LiveData<CategoriesState> by lazy {
        _categoriesState
    }

    private fun getScienceClubs() {
        viewModelScope.launch {
            repository.getScienceClubsPaged()
                .cachedIn(viewModelScope)
                .collectLatest {
                    _scienceClubs.postValue(it)
                }
        }
    }

    private fun getScienceClubTags(tagsLiveData: MutableLiveData<CategoriesState>) {
        viewModelScope.launch {
            val result = repository.getScienceClubTags()
            if (result.status == Resource.Status.SUCCESS) {
                tagsLiveData.postValue(CategoriesState(result.data?.map {
                    it.name
                } ?: listOf()))
            }
        }
    }

    fun setTextFilter(text: String) {
        // add text filter
    }

    fun toggleCategory(categoryName: String) {
        if (categoriesState.value?.selectedCategories?.contains(categoryName) == true) {
            _categoriesState.value =
                CategoriesState(
                    allCategories = categoriesState.value?.allCategories ?: listOf(),
                    selectedCategories = categoriesState.value?.selectedCategories?.minus(
                        categoryName
                    ) ?: listOf()
                )
        } else {
            _categoriesState.value = CategoriesState(
                allCategories = categoriesState.value?.allCategories ?: listOf(),
                selectedCategories = categoriesState.value?.selectedCategories?.plus(categoryName)
                    ?: (listOf<String>().plus(categoryName))
            )
        }
        setCategoriesFilter()
    }

    private fun setCategoriesFilter() {
        //TODO: Filter by category
    }

    data class CategoriesState(
        val allCategories: List<String>,
        val selectedCategories: List<String> = listOf()
    )
}
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

    private val _scienceClubTags by lazy {
        MutableLiveData<List<String>>()
    }

    val scienceClubTags by lazy {
        _scienceClubTags
    }

    private val _selectedCategories by lazy {
        MutableLiveData<List<String>>()
    }
    val selectedCategories: LiveData<List<String>> by lazy {
        _selectedCategories
    }

    init {
        getScienceClubs()
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

    fun getScienceClubTags(){
        getScienceClubTags(_scienceClubTags)
    }

    private fun getScienceClubTags(tagsLiveData: MutableLiveData<List<String>>) {
        viewModelScope.launch {
            val result = repository.getScienceClubTags()
            if (result.status == Resource.Status.SUCCESS) {
                tagsLiveData.postValue(result.data?.map {
                    it.name
                } ?: listOf())
            }
        }
    }

    fun setTextFilter(text: String) {
        // add text filter
    }

//    fun toggleCategory(categoryName: String) {
//        if (categoriesState.value?.selectedCategories?.contains(categoryName) == true) {
//            _categoriesState.value =
//                CategoriesState(
//                    allCategories = categoriesState.value?.allCategories ?: listOf(),
//                    selectedCategories = categoriesState.value?.selectedCategories?.minus(
//                        categoryName
//                    ) ?: listOf()
//                )
//        } else {
//            _categoriesState.value = CategoriesState(
//                allCategories = categoriesState.value?.allCategories ?: listOf(),
//                selectedCategories = categoriesState.value?.selectedCategories?.plus(categoryName)
//                    ?: (listOf<String>().plus(categoryName))
//            )
//        }
//        setCategoriesFilter()
//    }

    fun setCategoriesFilter(filters: List<String>) {
        _selectedCategories.postValue(filters)
    }

    data class CategoriesState(
        val allCategories: List<String>,
        val selectedCategories: List<String> = listOf()
    )
}
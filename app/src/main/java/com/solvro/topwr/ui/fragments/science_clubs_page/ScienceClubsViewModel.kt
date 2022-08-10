package com.solvro.topwr.ui.fragments.science_clubs_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.data.repository.MainRepository
import com.solvro.topwr.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScienceClubsViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _scienceClubs by lazy {
        MutableLiveData<Resource<List<ScienceClub>>>()
            .also {
                getScienceClubs(it)
            }
    }
    val scienceClubs: LiveData<Resource<List<ScienceClub>>> by lazy {
        _scienceClubs
    }

    private val _categoriesState by lazy {
        val categoriesMock = listOf(
            "Techologia",
            "Budownictwo",
            "Programowanie",
            "Druk 3D",
            "Motoryzacja"
        )
        MutableLiveData(CategoriesState(allCategories = categoriesMock))
    }
    val categoriesState: LiveData<CategoriesState> by lazy {
        _categoriesState
    }

    private fun getScienceClubs(scienceClubsLiveData: MutableLiveData<Resource<List<ScienceClub>>>) {
        viewModelScope.launch {
            val result = repository.getScienceClubs()
            scienceClubsLiveData.postValue(result)
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
package com.solvro.topwr.ui.fragments.science_clubs_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solvro.topwr.data.model.scienceClubs.ScienceClub
import com.solvro.topwr.data.repository.MainRepository
import com.solvro.topwr.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScienceClubsViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    private var scienceClubsAll = listOf<ScienceClub>()

    private var textFilter = ""

    private val _scienceClubs = repository.getScienceClubs()
    val scienceClubs: LiveData<Resource<List<ScienceClub>>> = _scienceClubs

    private val _categoriesState = MutableLiveData<CategoriesState>()
    val categoriesState: LiveData<CategoriesState> = _categoriesState

    init {
        val categoriesMock = listOf(
            "Techologia",
            "Budownictwo",
            "Programowanie",
            "Druk 3D",
            "Motoryzacja"
        )
        _categoriesState.value = CategoriesState(allCategories = categoriesMock)
        val scienceClubMock = listOf<ScienceClub>()
        scienceClubsAll = scienceClubMock
    }

    fun setTextFilter(text: String) {
        textFilter = text
//        _scienceClubs.value = scienceClubsAll.filter {
//            it.name?.lowercase()?.contains(textFilter) ?: false
//        }
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
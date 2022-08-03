package com.solvro.topwr.ui.fragments.science_clubs_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solvro.topwr.data.model.scienceclub.ScienceClub

class ScienceClubsViewModel : ViewModel() {
    private var scienceClubsAll = listOf<ScienceClub>()

    private var textFilter = ""

    private val _scienceClubs = MutableLiveData<List<ScienceClub>>()
    val scienceClubs: LiveData<List<ScienceClub>> = _scienceClubs

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
        val scienceClubMock = listOf(
            ScienceClub("1", "KN Solvro", "", "", "", "", listOf("")),
            ScienceClub("2", "SKN Gospodarki Przestrzennej", "", "", "", "", listOf("")),
            ScienceClub("3", "KN Projektantów Chemicznych “Consilium”", "", "", "", "", listOf("")),
            ScienceClub("4", "KN SISK", "", "", "", "", listOf(""))
        )
        scienceClubsAll = scienceClubMock
        _scienceClubs.value = scienceClubsAll
    }

    fun setTextFilter(text: String) {
        textFilter = text
        _scienceClubs.value = scienceClubsAll.filter {
            it.name.lowercase().contains(textFilter)
        }
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

    private fun setCategoriesFilter(){
        //TODO: Filter by category
    }

    data class CategoriesState(
        val allCategories: List<String>,
        val selectedCategories: List<String> = listOf()
    )
}
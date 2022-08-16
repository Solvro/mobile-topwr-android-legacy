package com.solvro.topwr.ui.fragments.science_clubs_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.data.repository.MainRepository
import com.solvro.topwr.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScienceClubsViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private var scienceClubJob: Job? = null

    private var lastTextFilter: String = ""

    /** LiveData */
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

    fun getScienceClubTags() {
        getScienceClubTags(_scienceClubTags)
    }

    fun setTextFilter(text: String) {
        lastTextFilter = text
        getScienceClubs(
            tagFilter = _selectedCategories.value?.firstOrNull(),
            textFilter = text
        )
    }

    fun setCategoriesFilter(tagFilter: String?) {
        _selectedCategories.postValue(
            if (tagFilter == null) listOf() else listOf(tagFilter)
        )
        getScienceClubs(
            tagFilter = tagFilter,
            textFilter = lastTextFilter,
        )
    }

    private fun getScienceClubs(tagFilter: String? = null, textFilter: String = "") {
        scienceClubJob?.cancel()
        scienceClubJob = if (tagFilter == null)
            getAllScienceClubs(textFilter)
        else
            getScienceClubsByTag(tagFilter, textFilter)
    }

    private fun getAllScienceClubs(textFilter: String) = viewModelScope.launch {
        repository.getScienceClubsPaged()
            .cancellable()
            .cachedIn(viewModelScope)
            .collectLatest {
                _scienceClubs.postValue(it.filter { scienceClub ->
                    scienceClub.name?.lowercase()?.contains(textFilter.lowercase()) ?: false
                })
            }
    }

    private fun getScienceClubsByTag(tagFilter: String, textFilter: String) =
        viewModelScope.launch {
            repository.getScienceClubsByTagPaged(tagFilter)
                .cancellable()
                .cachedIn(viewModelScope)
                .collectLatest {
                    _scienceClubs.postValue(it.filter { scienceClub ->
                        scienceClub.name?.lowercase()?.contains(textFilter.lowercase()) ?: false
                    })
                }
        }

    private fun getScienceClubTags(tagsLiveData: MutableLiveData<List<String>>) {
        viewModelScope.launch {
            val result = repository.getScienceClubTags()
            if (result.status == Resource.Status.SUCCESS) {
                tagsLiveData.postValue(result.data?.map {
                    it.name ?: ""
                } ?: listOf())
            }
        }
    }
}
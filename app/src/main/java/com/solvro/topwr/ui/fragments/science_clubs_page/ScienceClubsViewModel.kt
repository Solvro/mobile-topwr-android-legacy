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
import com.solvro.topwr.utils.Constants
import com.solvro.topwr.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
        scienceClubJob?.cancel()
        scienceClubJob = getAllScienceClubs(tagFilter, textFilter)
    }

    private fun getAllScienceClubs(tagFilter: String? = null, textFilter: String) =
        viewModelScope.launch {
            delay(Constants.DEFAULT_DEBOUNCE_TIME_MS)
            repository.getScienceClubsPaged()
                .cancellable()
                .cachedIn(viewModelScope)
                .collectLatest {
                    val filteredData = it.filter { scienceClub ->
                        if (tagFilter == null) true else scienceClub.isTaggedAs(tagFilter)
                    }.filter { scienceClub ->
                        scienceClub.name?.lowercase()?.contains(textFilter.lowercase()) ?: false
                    }
                    _scienceClubs.postValue(filteredData)
                }
        }

    private fun getScienceClubTags() {
        viewModelScope.launch {
            val result = repository.getScienceClubTags()
            if (result.status == Resource.Status.SUCCESS) {
                _scienceClubTags.postValue(result.data?.map {
                    it.name ?: ""
                } ?: listOf())
            }
        }
    }
}
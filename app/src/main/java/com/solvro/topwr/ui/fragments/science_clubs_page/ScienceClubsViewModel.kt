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

    private fun getScienceClubs(filter: String? = null) {
        scienceClubJob?.cancel()
        if (filter == null)
            scienceClubJob = viewModelScope.launch {
                repository.getScienceClubsPaged()
                    .cancellable()
                    .cachedIn(viewModelScope)
                    .collectLatest {
                        _scienceClubs.postValue(it)
                    }
            }
        else
            scienceClubJob = viewModelScope.launch {
                repository.getScienceClubsByTagPaged(filter)
                    .cancellable()
                    .cachedIn(viewModelScope)
                    .collectLatest {
                        _scienceClubs.postValue(it)
                    }
            }
    }

    fun getScienceClubTags() {
        getScienceClubTags(_scienceClubTags)
    }

    private fun getScienceClubTags(tagsLiveData: MutableLiveData<List<String>>) {
        viewModelScope.launch {
            val result = repository.getScienceClubTags()
            if (result.status == Resource.Status.SUCCESS) {
                tagsLiveData.postValue(result.data?.map {
                    it.name?: ""
                } ?: listOf())
            }
        }
    }

    fun setTextFilter(text: String) {
        // add text filter
    }

    fun setCategoriesFilter(filter: String?) {
        _selectedCategories.postValue(if (filter == null) listOf() else listOf(filter))
        getScienceClubs(filter)
    }
}
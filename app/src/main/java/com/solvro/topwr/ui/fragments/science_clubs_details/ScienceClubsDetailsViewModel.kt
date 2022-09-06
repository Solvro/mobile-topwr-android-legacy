package com.solvro.topwr.ui.fragments.science_clubs_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScienceClubsDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _scienceClub by lazy { MutableLiveData<ScienceClub>() }
    val scienceClub: LiveData<ScienceClub> by lazy { _scienceClub }

    init {
        val scienceClub = savedStateHandle.get<ScienceClub>("science_club")
        _scienceClub.value = scienceClub
    }
}
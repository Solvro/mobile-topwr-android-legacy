package com.solvro.topwr.features.faq.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.solvro.topwr.features.faq.domain.model.Info
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FaqDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val info: LiveData<Info?> = savedStateHandle.getLiveData("info", null)
}
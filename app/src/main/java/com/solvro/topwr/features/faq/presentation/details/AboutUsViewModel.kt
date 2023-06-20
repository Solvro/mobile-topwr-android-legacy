package com.solvro.topwr.features.faq.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.solvro.topwr.features.faq.domain.model.AboutUs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutUsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val aboutUs: LiveData<AboutUs?> = savedStateHandle.getLiveData("aboutUs", null)
}
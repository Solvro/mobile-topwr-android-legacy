package com.solvro.topwr.ui.fragments.about_us_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.solvro.topwr.data.model.aboutUs.AboutUs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutUsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val aboutUs: LiveData<AboutUs?> = savedStateHandle.getLiveData("aboutUs", null)
}
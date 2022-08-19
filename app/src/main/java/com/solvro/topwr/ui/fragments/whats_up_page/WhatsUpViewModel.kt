package com.solvro.topwr.ui.fragments.whats_up_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.solvro.topwr.data.model.notices.Notices
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WhatsUpViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val notice: LiveData<Notices?> = savedStateHandle.getLiveData("notice", null)
}
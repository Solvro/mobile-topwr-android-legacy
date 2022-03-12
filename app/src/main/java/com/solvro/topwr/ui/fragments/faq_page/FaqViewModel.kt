package com.solvro.topwr.ui.fragments.faq_page

import androidx.lifecycle.ViewModel
import com.solvro.topwr.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FaqViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    val infos = repository.getInfos()
}
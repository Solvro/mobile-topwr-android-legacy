package com.solvro.topwr.ui.fragments.home_page

import androidx.lifecycle.ViewModel
import com.solvro.topwr.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: MainRepository): ViewModel() {

}
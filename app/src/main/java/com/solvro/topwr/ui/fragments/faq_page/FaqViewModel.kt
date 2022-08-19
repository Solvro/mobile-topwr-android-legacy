package com.solvro.topwr.ui.fragments.faq_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solvro.topwr.data.model.info.Info
import com.solvro.topwr.data.repository.MainRepository
import com.solvro.topwr.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FaqViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private var textFilter = ""

    private val _infos by lazy { MutableLiveData<Resource<List<Info>>>() }
    val infos: LiveData<Resource<List<Info>>> by lazy { _infos }

    init {
        getInfos(_infos, textFilter)
    }

    private fun getInfos(infosLiveData: MutableLiveData<Resource<List<Info>>>, nameFilter: String) {
        viewModelScope.launch {
            val infosResource = repository.getInfos(nameFilter)
            infosLiveData.postValue(infosResource)
        }
    }

    fun setTextFilter(textFilter: String) {
        this.textFilter = textFilter
        getInfos(_infos, textFilter)
    }
}
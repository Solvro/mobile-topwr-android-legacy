package com.solvro.topwr.features.faq.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solvro.topwr.core.domain.model.Resource
import com.solvro.topwr.features.faq.domain.model.AboutUs
import com.solvro.topwr.features.faq.domain.model.Info
import com.solvro.topwr.features.faq.data.FaqRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FaqViewModel @Inject constructor(private val repository: FaqRepositoryImpl) : ViewModel() {

    private var textFilter = ""

    private val _infos by lazy { MutableLiveData<Resource<List<Info>>>() }
    val infos: LiveData<Resource<List<Info>>> by lazy { _infos }

    private val _about_us by lazy { MutableLiveData<AboutUs>() }
    val aboutUs: LiveData<AboutUs> by lazy { _about_us }

    init {
        getAboutUs()
        getInfos(_infos, textFilter)
    }

    private fun getInfos(infosLiveData: MutableLiveData<Resource<List<Info>>>, nameFilter: String) {
        viewModelScope.launch {
            val infosResource = repository.getInfos(nameFilter)
            infosLiveData.postValue(infosResource)
        }
    }

    fun setTextFilter(textFilter: String) {
        this.textFilter = textFilter.trim()
        getInfos(_infos, this.textFilter)
    }

    private fun getAboutUs() {
        viewModelScope.launch {
            val aboutUsInfo = repository.getAboutUs()
            handleAboutUsResponse(aboutUsInfo)
        }
    }

    private fun handleAboutUsResponse(response: Resource<AboutUs>) {
        when (response) {
            is Resource.Success -> {
                response.data.let {
                    _about_us.postValue(it)
                }
            }
            is Resource.Error -> {
                //TODO()
            }
            is Resource.Loading -> {
                //TODO()
            }
        }
    }
}
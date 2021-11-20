package com.solvro.topwr.ui.fragments.home_page

import android.util.Log
import androidx.lifecycle.*
import com.solvro.topwr.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: MainRepository) : ViewModel() {
    private var _endDate = MutableLiveData<String>()
    val endDate: LiveData<String>
        get() = _endDate
    val departments = repository.getDepartments()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getEndDate()
        }
    }

    private suspend fun getEndDate() {
        val dateObj = repository.getEndDate()
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).parse(dateObj.data?.EndDate!!)
        val curr_date = Calendar.getInstance().time
        val diff = Math.abs(date.time - curr_date.time)
        val days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        var daysString = ""
        if (days < 100) {
            daysString += "0" + days.toString()
        } else {
            daysString += days.toString()
        }
        _endDate.postValue(daysString)
    }


}
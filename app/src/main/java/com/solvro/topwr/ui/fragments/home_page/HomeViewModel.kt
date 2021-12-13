package com.solvro.topwr.ui.fragments.home_page

import android.util.Log
import androidx.lifecycle.*
import com.solvro.topwr.data.model.date.Date
import com.solvro.topwr.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    private var _endDate = MutableLiveData<String>()
    val endDate: LiveData<String>
        get() = _endDate
    val departments = repository.getDepartments()
    val buildings = repository.getMaps()
    val notices = repository.getNotices()
    val scienceClubs = repository.getScienceClubs()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            getEndDate()
        }
    }

    private suspend fun getEndDate() {
        val dateObj = repository.getEndDate()
        if (dateObj.data?.EndDate != null) {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).parse(dateObj.data.EndDate)
            val curr_date = Calendar.getInstance().time
            val diff = abs(date.time - curr_date.time)
            val days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
            var daysString = ""
            daysString += if (days < 100) {
                "0" + days.toString()
            } else {
                days.toString()
            }
            _endDate.postValue(daysString)
        }
    }

    fun getDayOfWeek(): LiveData<Date> {
        val calendar = Calendar.getInstance()
        val day = calendar[Calendar.DAY_OF_WEEK]
        val week = calendar.get(Calendar.WEEK_OF_YEAR) +1
        Log.i("Week", week.toString())
        val date = Date(day,week%2==0)
        return liveData {
            emit(date)
        }
    }
}
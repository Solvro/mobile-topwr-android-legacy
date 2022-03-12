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
    val departments = repository.getDepartments()
    val buildings = repository.getMaps()
    val notices = repository.getNotices()
    val scienceClubs = repository.getScienceClubs()
//    private var _dateWeek = MutableLiveData<Date>()
//    val dateWeek: LiveData<Date>
//        get() = _dateWeek
    private val _endDate = repository.getEndDate()
    val endDate : LiveData<String> = Transformations.switchMap(_endDate){dateObj ->
        var daysString = ""
        if (dateObj.data?.EndDate != null) {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).parse(dateObj.data.EndDate)
            val curr_date = Calendar.getInstance().time
            val diff = abs(date.time - curr_date.time)
            val days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
            daysString += if (days < 100) {
                "0" + days.toString()
            } else {
                days.toString()
            }
        }
        liveData<String> { emit(daysString) }
    }

    private val _dateWeek = repository.getWeekDayException()
    val dateWeek : LiveData<Date> = Transformations.switchMap(_dateWeek){exceptionObj ->
        var date = Date(0,false)
        if(exceptionObj.data != null && exceptionObj.data.Weekday != null){
            for (weekDay in exceptionObj.data.Weekday){
                val curr_date = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).format(Date())
                if(weekDay.Date.equals(curr_date))
                {
                    var dayOfWeek = 0
                    when(weekDay.DayOfTheWeek){
                        "Mon" -> dayOfWeek = Calendar.MONDAY
                        "Tue" -> dayOfWeek = Calendar.TUESDAY
                        "Wed" -> dayOfWeek = Calendar.WEDNESDAY
                        "Thu" -> dayOfWeek = Calendar.THURSDAY
                        "Fri" -> dayOfWeek = Calendar.FRIDAY
                    }
                    var parity :Boolean = true
                    when(weekDay.Parity){
                        "Odd" -> parity = false
                        "Even" -> parity = true
                    }
                    date = Date(dayOfWeek,parity)
                }
                else{
                    val calendar = Calendar.getInstance()
                    val day = calendar[Calendar.DAY_OF_WEEK]
                    val week = calendar.get(Calendar.WEEK_OF_YEAR) +1
                    Log.i("Week", week.toString())
                    date = Date(day,week%2==0)

                }
            }
        }
            liveData { emit(date) }

    }

}
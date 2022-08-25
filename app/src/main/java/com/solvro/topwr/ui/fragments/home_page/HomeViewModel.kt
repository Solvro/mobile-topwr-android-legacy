package com.solvro.topwr.ui.fragments.home_page

import android.util.Log
import androidx.lifecycle.*
import com.solvro.topwr.data.model.date.Date
import com.solvro.topwr.data.model.endDate.EndDate
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.data.repository.MainRepository
import com.solvro.topwr.utils.Constants
import com.solvro.topwr.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    /* LiveData */
    val departments = repository.getDepartments()
    val buildings = repository.getMaps()
    val notices = repository.getNotices()

    private val _scienceClubs by lazy {
        MutableLiveData<Resource<List<ScienceClub>>>()
            .also { getScienceClubs(it) }
    }
    val scienceClubs: LiveData<Resource<List<ScienceClub>>> by lazy { _scienceClubs }

    private val _endDate = repository.getEndDate()
    val endDate: LiveData<String> = Transformations.switchMap(_endDate) { dateObj ->
        var daysString = "0"
        if (dateObj.data?.endDate != null) {
            daysString = (calculateDaysToEndDate(dateObj.data) ?: "0")
                .padStart(3, '0')
        }
        liveData<String> { emit(daysString) }
    }

    private val _dateWeek = repository.getWeekDayException()
    val dateWeek: LiveData<Date> = Transformations.switchMap(_dateWeek) { exceptionObj ->
        // TODO: Split function
        val dayException = exceptionObj.data?.Weekday?.find {
            val currDate = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).format(Date())
            it.Date.equals(currDate)
        }
        val date: Date
        if (dayException != null) {
            var dayOfWeek = 0
            when (dayException.DayOfTheWeek) {
                "Mon" -> dayOfWeek = Calendar.MONDAY
                "Tue" -> dayOfWeek = Calendar.TUESDAY
                "Wed" -> dayOfWeek = Calendar.WEDNESDAY
                "Thu" -> dayOfWeek = Calendar.THURSDAY
                "Fri" -> dayOfWeek = Calendar.FRIDAY
            }
            var parity = true
            when (dayException.Parity) {
                "Odd" -> parity = false
                "Even" -> parity = true
            }
            date = Date(dayOfWeek, parity)
        } else {
            val calendar = Calendar.getInstance()
            val day = calendar[Calendar.DAY_OF_WEEK]
            val week = calendar.get(Calendar.WEEK_OF_YEAR) + 1
            Log.i("Week", week.toString())
            date = Date(day, week % 2 == 0)
        }
        liveData { emit(date) }
    }


    private fun getScienceClubs(scienceClubsLiveData: MutableLiveData<Resource<List<ScienceClub>>>) {
        scienceClubsLiveData.value = Resource(
            status = Resource.Status.LOADING,
            null,
            null
        )
        viewModelScope.launch {
            val response = repository.getScienceClubs()
            if (response.status == Resource.Status.SUCCESS) {
                scienceClubsLiveData.postValue(response)
            }
        }
    }

    private fun calculateDaysToEndDate(data: EndDate): String? {
        val dateFormatter = DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN)
        val endDate = LocalDate.parse(data.endDate, dateFormatter).minusDays(1)
        val currDate = LocalDate.now()
        val daysDiff = ChronoUnit.DAYS.between(currDate, endDate)
        return if (daysDiff < 0) null else daysDiff.toString()
    }
}
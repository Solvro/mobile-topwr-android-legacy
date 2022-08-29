package com.solvro.topwr.ui.fragments.home_page

import androidx.lifecycle.*
import com.solvro.topwr.data.model.date.Date
import com.solvro.topwr.data.model.endDate.EndDate
import com.solvro.topwr.data.model.endDate.Weekday
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.data.repository.MainRepository
import com.solvro.topwr.utils.AcademicDayMapper
import com.solvro.topwr.utils.Constants
import com.solvro.topwr.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoField
import org.threeten.bp.temporal.ChronoUnit
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
        val dateFormatter = DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN)
        val dayException = exceptionObj.data?.weekday?.find {
            val exceptionDate = LocalDate.parse(it.date, dateFormatter)
            val currDate = LocalDate.now()
            exceptionDate.equals(currDate)
        }
        val date = getAcademicDate(dayException)
        liveData { emit(date) }
    }

    private fun getAcademicDate(
        dayException: Weekday?
    ): Date {
        return if (dayException != null) {
            val dayOfWeek =
                AcademicDayMapper.mapWeekDayStringToCalendarWeekDay(dayException.dayOfTheWeek ?: "")
            val parity = AcademicDayMapper.mapParityToBoolean(dayException.parity ?: "")
            Date(dayOfWeek, parity)
        } else {
            val currDate = LocalDate.now()
            Date(
                currDate.dayOfWeek.value + 1,
                currDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR) % 2 == 0
            )
        }
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
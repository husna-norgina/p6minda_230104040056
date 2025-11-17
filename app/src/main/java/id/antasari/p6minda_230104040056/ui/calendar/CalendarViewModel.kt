package id.antasari.p6minda_230104040056.ui.calendar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import id.antasari.p6minda_230104040056.data.DiaryEntry
import id.antasari.p6minda_230104040056.data.MindaDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class CalendarViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = MindaDatabase.getInstance(app).diaryDao()

    /**
     * Flow: semua diary -> dikelompokkan per LocalDate (zona device),
     * diurutkan ascending per tanggal.
     */
    val diaryByDate: StateFlow<Map<LocalDate, List<DiaryEntry>>> =
        dao.observeAll() // Flow<List<DiaryEntry>>
            .map { list ->
                list
                    .groupBy { it.timestamp.toLocalDate() }
                    .mapValues { (_, v) -> v.sortedBy { it.timestamp } }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                emptyMap()
            )

    companion object {
        /**
         * Factory aman (tanpa Composable API)
         */
        fun provideFactory(app: Application): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    require(modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
                        "Unknown ViewModel class"
                    }
                    return CalendarViewModel(app) as T
                }
            }
    }
}

/** Helper konversi epochMillis -> LocalDate (zona device) */
private fun Long.toLocalDate(): LocalDate =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()

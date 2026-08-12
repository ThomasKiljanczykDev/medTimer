package com.futsch1.medtimer.feature.ui.statistics.calendar

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsch1.medtimer.core.common.di.Dispatcher
import com.futsch1.medtimer.core.common.di.MedTimerDispatchers
import com.futsch1.medtimer.feature.ui.statistics.CalendarEventsProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Drives the calendar for a single medicine, opened from the edit-medicine menu.
 *
 * It reads the same [CalendarEventsProvider] stream as the Analysis calendar; only the medicine
 * filter and the month window differ, and those come from the navigation arguments.
 */
@HiltViewModel
class MedicineCalendarViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    calendarEventsProvider: CalendarEventsProvider,
    @Dispatcher(MedTimerDispatchers.IO) ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val medicineId: Int = savedStateHandle[ARG_MEDICINE_ID] ?: ALL_MEDICINES
    private val _state = MutableMedicineCalendarScreenState(
        pastMonths = savedStateHandle[ARG_PAST_MONTHS] ?: DEFAULT_PAST_MONTHS,
        futureMonths = savedStateHandle[ARG_FUTURE_MONTHS] ?: DEFAULT_FUTURE_MONTHS,
    )
    val state: MedicineCalendarScreenState get() = _state

    init {
        calendarEventsProvider
            .structuredEventsFlow(medicineId, _state.pastMonths)
            .map { it.toImmutableMap() }
            .flowOn(ioDispatcher)
            .onEach { _state.dayEvents = it }
            .launchIn(viewModelScope)
    }

    companion object {
        const val ARG_MEDICINE_ID = "medicineId"
        const val ARG_PAST_MONTHS = "pastMonths"
        const val ARG_FUTURE_MONTHS = "futureMonths"

        private const val ALL_MEDICINES = -1
        private const val DEFAULT_PAST_MONTHS = 3
        private const val DEFAULT_FUTURE_MONTHS = 0
    }
}

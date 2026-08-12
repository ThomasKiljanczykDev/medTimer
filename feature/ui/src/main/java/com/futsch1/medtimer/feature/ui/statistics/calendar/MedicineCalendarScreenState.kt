package com.futsch1.medtimer.feature.ui.statistics.calendar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import java.time.LocalDate

/**
 * The single-medicine calendar's state contract — the read-only view the UI sees. The view-model owns
 * the mutable implementation ([MutableMedicineCalendarScreenState]) and is the only writer. See
 * `docs/guidelines/jetpack-compose.md` §State holders.
 */
interface MedicineCalendarScreenState {
    /** Months of history to show, from the navigation arguments. */
    val pastMonths: Int

    /** Months of simulated future reminders to show, from the navigation arguments. */
    val futureMonths: Int

    val dayEvents: ImmutableMap<LocalDate, List<CalendarDayEvent>>
}

/**
 * Mutable implementation owned by [MedicineCalendarViewModel].
 *
 * The month window comes from the navigation arguments and never changes for a given screen
 * instance, so it is held as a plain value rather than snapshot state.
 */
class MutableMedicineCalendarScreenState(
    override val pastMonths: Int,
    override val futureMonths: Int,
) : MedicineCalendarScreenState {
    override var dayEvents by mutableStateOf<ImmutableMap<LocalDate, List<CalendarDayEvent>>>(persistentMapOf())
}

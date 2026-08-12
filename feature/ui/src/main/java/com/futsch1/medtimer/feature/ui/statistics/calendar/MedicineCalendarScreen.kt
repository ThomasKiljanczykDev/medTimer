package com.futsch1.medtimer.feature.ui.statistics.calendar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.futsch1.medtimer.core.ui.preview.MedTimerPreview
import com.futsch1.medtimer.core.ui.theme.MedTimerTheme
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableMap
import java.time.LocalDate

/** Stateful entry point: binds the ViewModel to the stateless [MedicineCalendarScreen]. */
@Composable
fun MedicineCalendarScreen(
    viewModel: MedicineCalendarViewModel,
    modifier: Modifier = Modifier,
) {
    MedicineCalendarScreen(state = viewModel.state, modifier = modifier)
}

/** Stateless screen — the `@Preview`/test target. Renders purely from its inputs. */
@Composable
fun MedicineCalendarScreen(
    state: MedicineCalendarScreenState,
    modifier: Modifier = Modifier,
) {
    CalendarContent(
        dayEvents = state.dayEvents,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp, 4.dp, 16.dp, 16.dp),
        pastMonths = state.pastMonths,
        futureMonths = state.futureMonths,
    )
}

@MedTimerPreview
@Composable
private fun MedicineCalendarScreenPreview() {
    val today = LocalDate.now()
    val state = object : MedicineCalendarScreenState {
        override val pastMonths = 3
        override val futureMonths = 1
        override val dayEvents = persistentMapOf(
            today to listOf(
                CalendarDayEvent(today.atTime(8, 0), "1 tablet", "Vitamin X 500 mg", CalendarDayEvent.Status.TAKEN),
            ),
        ).toImmutableMap()
    }
    MedTimerTheme {
        Surface {
            MedicineCalendarScreen(state = state)
        }
    }
}

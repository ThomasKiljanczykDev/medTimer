package com.futsch1.medtimer.robots

import androidx.compose.ui.test.hasTestTag
import androidx.test.espresso.Espresso.pressBack
import com.futsch1.medtimer.core.ui.ScreenTestTags
import com.futsch1.medtimer.feature.ui.statistics.calendar.CalendarTestTags
import com.futsch1.medtimer.core.ui.R as CoreUiR

/** A medicine's own calendar, opened from the edit-medicine menu. */
class MedicineCalendarRobot(private val ui: ComposeUi, private val menus: MenuRobot) {

    private val screen get() = ui.scope(ScreenTestTags.MEDICINE_CALENDAR)

    fun assertDayEventsContain(expected: String) = inCalendar {
        screen.await { screen.textsUnder(DAY_EVENTS).any { it.contains(expected) } }
    }

    /** Opening and closing it is the assertion: the calendar has crashed on some reminder types. */
    fun assertOpens() = inCalendar { screen.self().assertExists() }

    private fun inCalendar(block: () -> Unit) {
        menus.clickEditMedicineOption(CoreUiR.string.calendar)
        block()
        pressBack()
    }

    private companion object {
        val DAY_EVENTS = hasTestTag(CalendarTestTags.DAY_EVENTS)
    }
}

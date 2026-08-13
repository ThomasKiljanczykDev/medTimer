package com.futsch1.medtimer.feature.ui.preferences

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.collections.immutable.persistentListOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals
import com.futsch1.medtimer.core.ui.R as CoreUiR

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class SettingsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun `shows the title and summary of every entry`() {
        composeTestRule.setContent {
            SettingsScreen(
                persistentListOf(
                    SettingsEntry(CoreUiR.string.alarm_settings, CoreUiR.drawable.alarm) {},
                    SettingsEntry(
                        CoreUiR.string.weekend_mode,
                        CoreUiR.drawable.calendar_week,
                        CoreUiR.string.weekend_mode_summary,
                    ) {},
                )
            )
        }

        composeTestRule.onNodeWithText(context.getString(CoreUiR.string.alarm_settings)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(CoreUiR.string.weekend_mode)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(CoreUiR.string.weekend_mode_summary)).assertIsDisplayed()
    }

    @Test
    fun `tapping an entry invokes its action`() {
        var clicked = 0
        composeTestRule.setContent {
            SettingsScreen(
                persistentListOf(
                    SettingsEntry(CoreUiR.string.alarm_settings, CoreUiR.drawable.alarm) { clicked++ },
                )
            )
        }

        composeTestRule.onNodeWithText(context.getString(CoreUiR.string.alarm_settings)).performClick()

        assertEquals(1, clicked)
    }
}

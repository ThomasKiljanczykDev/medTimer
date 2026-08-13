package com.futsch1.medtimer.feature.ui.preferences

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.futsch1.medtimer.core.ui.component.PreferenceItem
import com.futsch1.medtimer.core.ui.preview.MedTimerPreview
import com.futsch1.medtimer.core.ui.theme.MedTimerTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import com.futsch1.medtimer.core.ui.R as CoreUiR

/** One row of the settings root — a labelled link into a sub-screen. */
data class SettingsEntry(
    @param:StringRes val title: Int,
    @param:DrawableRes val icon: Int,
    @param:StringRes val summary: Int? = null,
    val onClick: () -> Unit,
)

/** The app settings root: the list of sub-screens, with no settable values of its own. */
@Composable
fun SettingsScreen(entries: ImmutableList<SettingsEntry>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        for (entry in entries) {
            PreferenceItem(
                title = stringResource(entry.title),
                summary = entry.summary?.let { stringResource(it) },
                icon = painterResource(entry.icon),
                onClick = entry.onClick,
            )
        }
    }
}

@MedTimerPreview
@Composable
private fun SettingsScreenPreview() {
    MedTimerTheme {
        Surface {
            SettingsScreen(
                persistentListOf(
                    SettingsEntry(CoreUiR.string.notification_reminder_settings, CoreUiR.drawable.bell) {},
                    SettingsEntry(CoreUiR.string.alarm_settings, CoreUiR.drawable.alarm) {},
                    SettingsEntry(
                        CoreUiR.string.weekend_mode,
                        CoreUiR.drawable.calendar_week,
                        CoreUiR.string.weekend_mode_summary,
                    ) {},
                )
            )
        }
    }
}

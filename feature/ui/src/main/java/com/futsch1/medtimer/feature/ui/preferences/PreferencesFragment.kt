package com.futsch1.medtimer.feature.ui.preferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.futsch1.medtimer.core.ui.ScreenTestTags
import com.futsch1.medtimer.core.ui.component.MedTimerTopAppBar
import com.futsch1.medtimer.core.ui.theme.MedTimerTheme
import com.futsch1.medtimer.feature.ui.R
import kotlinx.collections.immutable.persistentListOf
import com.futsch1.medtimer.core.ui.R as CoreUiR

/** Hosts the settings root. The screen itself is Compose; this fragment is the nav destination. */
class PreferencesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            val navController = findNavController()
            MedTimerTheme {
                Column(Modifier.testTag(ScreenTestTags.SETTINGS)) {
                    MedTimerTopAppBar(
                        title = stringResource(CoreUiR.string.tab_settings),
                        onNavigateUp = if (navController.previousBackStackEntry != null) {
                            { navController.navigateUp() }
                        } else {
                            null
                        },
                    )
                    SettingsScreen(entries = settingsEntries(navController))
                }
            }
        }
    }

    private fun settingsEntries(navController: NavController) = persistentListOf(
        SettingsEntry(CoreUiR.string.notification_reminder_settings, CoreUiR.drawable.bell) {
            navController.navigateSafely(R.id.action_preferencesFragment_to_notificationSettingsFragment)
        },
        SettingsEntry(CoreUiR.string.alarm_settings, CoreUiR.drawable.alarm) {
            navController.navigateSafely(R.id.action_preferencesFragment_to_alarmSettingsFragment)
        },
        SettingsEntry(CoreUiR.string.snooze_settings, CoreUiR.drawable.snooze) {
            navController.navigateSafely(R.id.action_preferencesFragment_to_snoozeSettingsFragment)
        },
        SettingsEntry(CoreUiR.string.display_settings, CoreUiR.drawable.display) {
            navController.navigateSafely(R.id.action_preferencesFragment_to_displaySettingsFragment)
        },
        SettingsEntry(CoreUiR.string.privacy_settings, CoreUiR.drawable.shield_lock) {
            navController.navigateSafely(R.id.action_preferencesFragment_to_privacyPreferencesFragment)
        },
        SettingsEntry(
            CoreUiR.string.weekend_mode,
            CoreUiR.drawable.calendar_week,
            CoreUiR.string.weekend_mode_summary,
        ) {
            navController.navigateSafely(R.id.action_preferencesFragment_to_weekendModePreferencesFragment)
        },
    )
}

/** A second tap before the first destination is on screen has no matching action; the monkey test finds this. */
private fun NavController.navigateSafely(@IdRes actionId: Int) {
    try {
        navigate(actionId)
    } catch (_: IllegalArgumentException) {
        // Intentionally empty
    }
}

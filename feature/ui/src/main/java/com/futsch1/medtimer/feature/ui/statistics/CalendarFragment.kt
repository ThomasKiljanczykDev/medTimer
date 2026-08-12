package com.futsch1.medtimer.feature.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.futsch1.medtimer.core.ui.ScreenTestTags
import com.futsch1.medtimer.core.ui.component.MedTimerTopAppBar
import com.futsch1.medtimer.core.ui.theme.MedTimerTheme
import com.futsch1.medtimer.feature.reminders.api.SimulatedReminders
import com.futsch1.medtimer.feature.ui.statistics.calendar.MedicineCalendarScreen
import com.futsch1.medtimer.feature.ui.statistics.calendar.MedicineCalendarViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.futsch1.medtimer.core.ui.R as CoreUiR

/** Hosts the single-medicine calendar. The screen itself is Compose; this fragment is the nav destination. */
@AndroidEntryPoint
class CalendarFragment : Fragment() {
    @Inject
    lateinit var simulatedRemindersRepository: SimulatedReminders

    private val viewModel: MedicineCalendarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            MedTimerTheme {
                Column(Modifier.testTag(ScreenTestTags.MEDICINE_CALENDAR)) {
                    MedTimerTopAppBar(
                        title = stringResource(CoreUiR.string.calendar),
                        onNavigateUp = { findNavController().navigateUp() },
                    )
                    MedicineCalendarScreen(viewModel = viewModel)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val futureMonths = viewModel.state.futureMonths
        if (futureMonths > 0) {
            simulatedRemindersRepository.requestWindow("calendar", (futureMonths.toLong() + 2) * 31)
        }
    }

    override fun onStop() {
        super.onStop()
        simulatedRemindersRepository.releaseWindow("calendar")
    }
}

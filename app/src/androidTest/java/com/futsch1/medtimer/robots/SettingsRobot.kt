package com.futsch1.medtimer.robots

import androidx.annotation.StringRes
import androidx.compose.ui.test.hasText
import androidx.test.espresso.Espresso.pressBack
import com.futsch1.medtimer.core.ui.ScreenTestTags

/**
 * The app settings. The interface is the setting to change; finding it and coming back out again is
 * the implementation, so a new nesting level does not move where every caller ends up.
 */
class SettingsRobot(
    private val ui: ComposeUi,
    private val menus: MenuRobot,
    private val preferences: PreferenceScreenRobot,
) {

    /**
     * Opens the settings, walks down [path] of nested preference screens, runs [block] there, and
     * returns to the screen the caller started from.
     */
    fun inSection(@StringRes vararg path: Int, block: () -> Unit) {
        menus.clickAppOption(com.futsch1.medtimer.core.ui.R.string.tab_settings)
        // The settings root is Compose; everything below it is still an androidx preference screen.
        path.forEachIndexed { index, titleRes ->
            if (index == 0) clickRootRow(titleRes) else preferences.click(titleRes)
        }
        block()
        repeat(path.size + 1) { pressBack() }
    }

    /** Clicks the last entry of [path], with the entries before it read as nested screens. */
    fun click(@StringRes vararg path: Int) {
        val sections = path.dropLast(1).toIntArray()
        inSection(*sections) { preferences.click(path.last()) }
    }

    private fun clickRootRow(@StringRes titleRes: Int) =
        ui.scope(ScreenTestTags.SETTINGS).click(hasText(ui.getString(titleRes)))
}

package com.futsch1.medtimer.feature.ui.preferences

import androidx.annotation.IdRes
import androidx.navigation.Navigation.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

/** Makes the [preferenceKey] row of [preferencesFragment] navigate along [actionId] when tapped. */
fun setupPreferencesLink(
    preferencesFragment: PreferenceFragmentCompat,
    preferenceKey: String,
    @IdRes actionId: Int
) {
    val preference = preferencesFragment.findPreference<Preference?>(preferenceKey)
    preference?.onPreferenceClickListener = Preference.OnPreferenceClickListener { _ ->
        val navController = findNavController(preferencesFragment.requireView())
        try {
            navController.navigate(actionId)
        } catch (_: IllegalArgumentException) {
            // Intentionally empty (monkey test can cause this to fail)
        }
        true
    }
}

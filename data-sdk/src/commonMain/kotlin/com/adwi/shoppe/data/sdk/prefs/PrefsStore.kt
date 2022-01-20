package com.adwi.shoppe.data.sdk.prefs

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.ObservableSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalSettingsApi
@ExperimentalSettingsImplementation
class PrefsStore(
    private val factory: SettingsFactory,
    private val settings: ObservableSettings = factory.getSettings(),
) {

    companion object {
        const val USER_DATA_PREFS = "userDataPreferences"
    }

    fun clear() {
        settings.clear()
    }
}
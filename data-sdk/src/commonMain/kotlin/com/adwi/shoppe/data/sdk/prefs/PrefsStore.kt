package com.adwi.shoppe.data.sdk.prefs

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.getStringFlow
import com.russhwolf.settings.nullableString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map

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

    var token by settings.nullableString()

    val tokenAsFlow = settings.getStringFlow("token").map { it.isNotEmpty() }

    fun clear() {
        settings.clear()
    }
}
package com.adwi.shoppe.data.sdk.prefs

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.JvmPreferencesSettings
import com.russhwolf.settings.ObservableSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.DIAware
import java.util.prefs.Preferences

@ExperimentalCoroutinesApi
@ExperimentalSettingsImplementation
@ExperimentalSettingsApi
actual class SettingsFactory actual constructor(override val di: DI) : DIAware {

    actual fun getSettings(): ObservableSettings = JvmPreferencesSettings(
        Preferences.userRoot()
            .node(PrefsStore.USER_DATA_PREFS)
    )
}
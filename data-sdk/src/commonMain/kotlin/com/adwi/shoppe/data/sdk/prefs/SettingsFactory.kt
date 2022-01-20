package com.adwi.shoppe.data.sdk.prefs

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import org.kodein.di.DI
import org.kodein.di.DIAware

@ExperimentalSettingsApi
expect class SettingsFactory(di: DI) : DIAware {
    fun getSettings(): ObservableSettings
}
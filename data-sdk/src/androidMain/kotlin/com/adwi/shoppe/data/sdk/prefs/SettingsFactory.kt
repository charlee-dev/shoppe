package com.adwi.shoppe.data.sdk.prefs

import android.content.Context
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.ObservableSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

@ExperimentalSettingsImplementation
@ExperimentalCoroutinesApi
@ExperimentalSettingsApi
actual class SettingsFactory actual constructor(override val di: DI) : DIAware {

    private val context: Context by instance()

    actual fun getSettings(): ObservableSettings = AndroidSettings(
        delegate = context.getSharedPreferences(
            PrefsStore.USER_DATA_PREFS,
            Context.MODE_PRIVATE
        )
    )
}
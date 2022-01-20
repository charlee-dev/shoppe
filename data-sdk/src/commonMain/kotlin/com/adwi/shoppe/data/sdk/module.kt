package com.adwi.shoppe.data.sdk

import com.adwi.shoppe.data.sdk.packages.PackageManager
import com.adwi.shoppe.data.sdk.prefs.PrefsStore
import com.adwi.shoppe.data.sdk.prefs.SettingsFactory
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

@ExperimentalSettingsImplementation
@ExperimentalCoroutinesApi
@ExperimentalSettingsApi
val sdkDataModule = DI.Module("sdkData") {
    bindSingleton { PackageManager(di) }
    bindSingleton { SettingsFactory(di) }
    bindSingleton { PrefsStore(instance()) }
}
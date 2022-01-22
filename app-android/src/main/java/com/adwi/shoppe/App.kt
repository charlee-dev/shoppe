package com.adwi.shoppe

import android.app.Application
import android.content.Context
import co.touchlab.kermit.ExperimentalKermitApi
import co.touchlab.kermit.Logger
import co.touchlab.kermit.platformLogWriter
import com.adwi.shoppe.feature.root.rootComponentModule
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bindInstance

@ApolloExperimental
@ExperimentalCoroutinesApi
@ExperimentalSettingsImplementation
@ExperimentalSettingsApi
@ExperimentalKermitApi
class App : Application(), DIAware {

    override val di by DI.lazy {

        import(rootComponentModule)

        bindInstance<Context> { this@App }
    }

    override fun onCreate() {
        super.onCreate()
        Logger.setLogWriters(platformLogWriter())
    }
}
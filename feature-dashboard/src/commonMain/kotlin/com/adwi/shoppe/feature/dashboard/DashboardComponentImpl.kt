package com.adwi.shoppe.feature.dashboard

import co.touchlab.kermit.Logger
import com.adwi.shoppe.data.sdk.prefs.PrefsStore
import com.adwi.shoppe.repository.AuthRepository
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

@ExperimentalSettingsImplementation
@ExperimentalSettingsApi
@ApolloExperimental
@ExperimentalCoroutinesApi
class DashboardComponentImpl(
    override val di: DI,
    private val componentContext: ComponentContext,
) : DashboardComponent, DIAware, ComponentContext by componentContext {

    private val authRepository by di.instance<AuthRepository>()
    private val prefsStore by di.instance<PrefsStore>()

    override val routerState: Value<RouterState<*, DashboardComponent.Child>>
        get() = TODO("Not yet implemented")

    override fun singOut() {
        authRepository.deleteUserState()
        prefsStore.token = ""
        Logger.v("DashboardComponentImpl = signOut")
    }
}
package com.adwi.shoppe.feature.root.store

import co.touchlab.kermit.Logger
import com.adwi.shoppe.data.sdk.prefs.PrefsStore
import com.adwi.shoppe.repository.AuthRepository
import com.adwi.shoppe.utils.AppCoroutineDispatcher
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalSettingsImplementation
@ExperimentalSettingsApi
@ExperimentalCoroutinesApi
@ApolloExperimental
internal class RootStoreFactory(
    private val storeFactory: StoreFactory,
    private val prefsStore: PrefsStore,
    private val authRepository: AuthRepository,
    private val onAuthenticationChanged: (Boolean) -> Unit,
) {
    fun create(): RootStore =
        object : RootStore, Store<RootStore.Intent, Unit, Unit> by storeFactory.create(
            RootStore::class.simpleName,
            initialState = Unit,
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
        ) {}

    private inner class ExecutorImpl : CoroutineExecutor<RootStore.Intent, Unit, Unit, Unit, Unit>(
        AppCoroutineDispatcher.Main
    ) {
        // Monitors authentication state
        override fun executeAction(action: Unit, getState: () -> Unit) {
            scope.launch {
                prefsStore.tokenAsFlow.collect { isSignedIn ->
                    Logger.v("token = $isSignedIn")
                    if (!isSignedIn) {
                        Logger.v("deleting userState")
                        authRepository.deleteUserState()
                    }
                    onAuthenticationChanged(isSignedIn)
                }
            }
        }
    }
}
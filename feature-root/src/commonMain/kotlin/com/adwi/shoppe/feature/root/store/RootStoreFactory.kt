package com.adwi.shoppe.feature.root.store

import com.adwi.shoppe.data.remote.type.UserInput
import com.adwi.shoppe.data.sdk.prefs.PrefsStore
import com.adwi.shoppe.repository.AuthRepository
import com.adwi.shoppe.utils.AppCoroutineDispatcher
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalSettingsImplementation
@ExperimentalSettingsApi
@ExperimentalCoroutinesApi
@ApolloExperimental
internal class RootStoreFactory(
    private val storeFactory: StoreFactory,
    private val prefsStore: PrefsStore,
    private val authRepository: AuthRepository,
    private val onSessionChanged: (Boolean) -> Unit,
) {
    fun create(): RootStore =
        object : RootStore, Store<RootStore.Intent, Unit, Unit> by storeFactory.create(
            RootStore::class.simpleName,
            initialState = Unit,
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
        ) {}

    private inner class ExecutorImpl : SuspendExecutor<RootStore.Intent, Unit, Unit, Unit, Unit>(
        AppCoroutineDispatcher.Main
    ) {
        // Monitors authentication state
        override suspend fun executeAction(action: Unit, getState: () -> Unit) {
            prefsStore.isSessionActive.collect { isActive ->
                if (!isActive) authRepository.deleteUserState()
                onSessionChanged(isActive)
            }
        }

        override suspend fun executeIntent(intent: RootStore.Intent, getState: () -> Unit) {
            when (intent) {
                is RootStore.Intent.signIn -> {
                    val result = authRepository.signIn(UserInput(intent.email, intent.password))
                    if (result.isEmpty()) {
                        prefsStore.token = result
                    }
                }
                is RootStore.Intent.signUp -> {
                    val result = authRepository.signUp(UserInput(intent.email, intent.password))
                    if (result.isEmpty()) {
                        prefsStore.token = result
                    }
                }
            }
        }
    }
}
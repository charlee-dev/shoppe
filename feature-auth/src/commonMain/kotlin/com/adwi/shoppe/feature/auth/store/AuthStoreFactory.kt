package com.adwi.shoppe.feature.auth.store

import com.adwi.shoppe.data.remote.type.UserInput
import com.adwi.shoppe.data.sdk.prefs.PrefsStore
import com.adwi.shoppe.feature.auth.store.AuthStore.Intent
import com.adwi.shoppe.feature.auth.store.AuthStore.Label
import com.adwi.shoppe.feature.auth.store.AuthStore.Result
import com.adwi.shoppe.feature.auth.store.AuthStore.State
import com.adwi.shoppe.repository.AuthRepository
import com.adwi.shoppe.utils.AppCoroutineDispatcher
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalSettingsImplementation
@ExperimentalSettingsApi
@ApolloExperimental
@ExperimentalCoroutinesApi
internal class AuthStoreFactory(
    private val storeFactory: StoreFactory,
    private val authRepository: AuthRepository,
    private val prefs: PrefsStore,
) {
    fun create(): AuthStore = object : AuthStore, Store<Intent, State, Label> by storeFactory.create(
        name = AuthStore::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl
    ) {}

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Result, Label>(
        AppCoroutineDispatcher.Main
    ) {
        override fun executeAction(action: Unit, getState: () -> State) {
            scope.launch {
                prefs.tokenAsFlow.collect { token ->
                    if (token != null) {
                        authRepository.getUserState()?.also { userState ->
                            prefs.token = userState.token
                        }
                    }
                }
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.ChangeEmail -> dispatch(Result.EmailChanged(intent.email))
                is Intent.ChangePassword -> dispatch(Result.PasswordChanged(intent.password))
                Intent.SignIn -> scope.launch { signIn(getState().email, getState().password) }
                Intent.SignUp -> scope.launch { signUp(getState().email, getState().password) }
            }
        }

        private suspend fun signIn(
            email: String,
            password: String,
        ) {
            launch {
                authRepository.signIn(UserInput(email, password)).also { token ->
                    prefs.token = token
                }
            }
        }

        private suspend fun signUp(
            email: String,
            password: String,
        ) {
            launch {
                authRepository.signUp(UserInput(email, password)).also { token ->
                    prefs.token = token
                }
            }
        }

        private suspend fun launch(
            unit: suspend () -> Unit,
        ) {
            try {
                dispatch(Result.Loading(true))
                withContext(AppCoroutineDispatcher.IO) { unit() }
            } catch (e: Exception) {
                publish(Label.MessageReceived(e.message))
            } finally {
                dispatch(Result.Loading(false))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Result> {
        override fun State.reduce(msg: Result): State = when (msg) {
            is Result.EmailChanged -> copy(email = msg.login)
            is Result.PasswordChanged -> copy(password = msg.password)
            is Result.Loading -> copy(isLoading = msg.isLoading)
        }
    }
}
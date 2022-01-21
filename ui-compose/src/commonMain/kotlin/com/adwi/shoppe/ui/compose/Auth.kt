package com.adwi.shoppe.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.shoppe.feature.auth.AuthComponent
import com.adwi.shoppe.ui.compose.resources.Resources
import kotlinx.coroutines.flow.collect

@Composable
fun AuthContent(
    component: AuthComponent,
    topInset: Dp,
    bottomInset: Dp,
) {
    Scaffold(
        topBar = { AuthBar(topInset = topInset) },
        snackbarHost = { hostState ->
            AuthMessage(
                hostState = hostState,
                component = component,
                bottomInset = bottomInset
            )
        },
        content = { AuthBody(component = component) },
    )
}

@Composable
private fun AuthBar(
    topInset: Dp,
) {
    TopAppBar(
        title = {
            Text(
                text = Resources.strings.auth,
                modifier = Modifier.padding(top = topInset)
            )
        },
        modifier = Modifier.height(Resources.dimens.barHeight + topInset)
    )
}

@Composable
private fun AuthBody(
    component: AuthComponent,
) {

    val model by component.model.collectAsState(AuthComponent.Model())

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            label = { Text(Resources.strings.email) },
            value = model.email,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false
            ),
            onValueChange = {
                component.onLoginChanged(it)
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            label = { Text(Resources.strings.password) },
            value = model.password,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = {
                component.onPasswordChanged(it)
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                component.onSignIn()
            },
            enabled = !model.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(56.dp)
        ) {
            Text(Resources.strings.sign_in)
        }
        Button(
            onClick = {
                component.onSignUp()
            },
            enabled = !model.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(56.dp)
        ) {
            Text(Resources.strings.register)
        }

        if (model.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun AuthMessage(
    component: AuthComponent,
    hostState: SnackbarHostState,
    bottomInset: Dp,
) {
    SnackbarHost(
        hostState = hostState,
        snackbar = {
            Snackbar(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(hostState.currentSnackbarData?.message ?: "")
            }
        },
        modifier = Modifier.padding(bottom = bottomInset)
    )

    LaunchedEffect("showError") {
        component.events.collect { event ->
            when (event) {
                is AuthComponent.Event.MessageReceived -> {
                    hostState.showSnackbar(event.message ?: "")
                }
            }
        }
    }
}
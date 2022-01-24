package com.adwi.shoppe.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.adwi.shoppe.feature.auth.AuthComponent
import com.adwi.shoppe.ui.compose.composables.BottomText
import com.adwi.shoppe.ui.compose.composables.ShoppeButton
import com.adwi.shoppe.ui.compose.composables.ShoppeSnackBarHost
import com.adwi.shoppe.ui.compose.composables.ShoppeSpacer
import com.adwi.shoppe.ui.compose.composables.ShoppeTextField
import com.adwi.shoppe.ui.compose.composables.WelcomeHeader

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun AuthContent(
    component: AuthComponent,
) {
    val model by component.model.collectAsState(AuthComponent.Model())

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        snackbarHost = { hostState ->
            ShoppeSnackBarHost(
                hostState = hostState,
                component = component,
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(400.dp).align(Alignment.Center)) {
                ShoppeSpacer(modifier = Modifier.weight(1f))
                WelcomeHeader()
                ShoppeSpacer(modifier = Modifier.weight(1f))
                ShoppeTextField(
                    text = model.email,
                    onTextChange = { component.onLoginChanged(it) },
                    label = "Email",
                    leadingIcon = Icons.Filled.Email,
                    backgroundColor = MaterialTheme.colors.secondaryVariant,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                        autoCorrect = false),
                    keyboardActions = KeyboardActions(onNext = { defaultKeyboardAction(ImeAction.Next) },
                        onSend = { clearFocus(keyboardController, focusManager) }),
                    modifier = Modifier.fillMaxWidth(.8f))
                ShoppeSpacer(16)
                ShoppeTextField(
                    text = model.password,
                    onTextChange = { component.onPasswordChanged(it) },
                    label = "Password",
                    leadingIcon = Icons.Filled.Password,
                    backgroundColor = MaterialTheme.colors.secondaryVariant,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,
                        imeAction = if (model.email.isEmpty()) ImeAction.Previous else ImeAction.Send,
                        autoCorrect = false),
                    keyboardActions = KeyboardActions(onPrevious = { focusManager.moveFocus(FocusDirection.Up) },
                        onSend = {
                            clearFocus(keyboardController, focusManager)
                        }),
                    modifier = Modifier.fillMaxWidth(.8f))
                ShoppeSpacer(32)
                ShoppeButton(
                    label = { Text(text = "Sign in") },
                    onClick = {
                        clearFocus(keyboardController, focusManager)
                        component.onSignIn()
                    },
                    isLoading = model.isLoading
                )
                ShoppeSpacer(modifier = Modifier.weight(2f))
            }
            BottomText(
                onClick = { component.onSignUp() },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 46.dp)
            )
        }
    }
}

@ExperimentalComposeUiApi
fun clearFocus(keyboardController: SoftwareKeyboardController?, focusManager: FocusManager) {
    keyboardController?.hide()
    focusManager.clearFocus()
}
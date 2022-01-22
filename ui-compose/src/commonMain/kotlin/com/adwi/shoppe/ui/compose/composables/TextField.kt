package com.adwi.shoppe.ui.compose.composables

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun ShoppeTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    label: String = "",
    shape: Shape = RoundedCornerShape(percent = 20),
    elevation: Dp = 10.dp,
    leadingIcon: ImageVector = Icons.Filled.Email,
    backgroundColor: Color = MaterialTheme.colors.secondaryVariant,
    contentColor: Color = MaterialTheme.colors.onSecondary,
    cursorColor: Color = MaterialTheme.colors.primary,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    layoutId: String = "",
) {
    val focusRequester = FocusRequester()
    var focusState by remember { mutableStateOf(false) }

    val scaleState by animateFloatAsState(
        targetValue = if (focusState) 1.02f else 1f,
        animationSpec = tween(500)
    )

    val transition = updateTransition(targetState = text.isNotEmpty(), label = "No text")
    val alpha by transition.animateFloat(label = "", transitionSpec = { tween(300) }
    ) { if (it) .5f else 0f }

    Surface(
        shape = shape,
        elevation = elevation,
        color = backgroundColor,
        modifier = modifier
            .layoutId(layoutId)
            .graphicsLayer {
                scaleY = scaleState
                scaleX = scaleState
            },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.weight(1f)) {
                TextField(
                    value = text,
                    onValueChange = onTextChange,
                    label = {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.body2.copy(color = contentColor),
                        )
                    },
                    singleLine = true,
                    maxLines = 1,
                    leadingIcon = {
                        Icon(
                            imageVector = leadingIcon,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier
                        )
                    },
                    trailingIcon = {
//                        if (text.isNotEmpty()) {
                        Surface(
                            onClick = { if (text.isNotEmpty()) onTextChange("") },
                            color = backgroundColor,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear text input",
                                tint = cursorColor,
                                modifier = Modifier.alpha(alpha)
                            )
                        }
//                        }
                    },
                    visualTransformation = visualTransformation,
                    keyboardActions = keyboardActions,
                    keyboardOptions = keyboardOptions,
                    textStyle = MaterialTheme.typography.body2.copy(color = contentColor),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = cursorColor,
                        backgroundColor = backgroundColor,
                        unfocusedIndicatorColor = backgroundColor,
                        focusedIndicatorColor = backgroundColor
                    ),

                    modifier = Modifier
                        .padding(horizontal = 32.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState = it.isFocused },
                )
            }
        }
    }
}
package com.adwi.shoppe.ui.compose

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.shoppe.feature.root.RootComponent
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import org.kodein.di.DI
import org.kodein.di.compose.withDI

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun RootContent(
    di: DI,
    component: RootComponent,
    windowWidth: Dp,
    topInset: Dp = 0.dp,
    bottomInset: Dp = 0.dp,
) = withDI(di) {
    Children(component.routerState) {
        it.instance.let { child ->
            when (child) {
                is RootComponent.Child.Splash -> SplashContent(child.component)
                is RootComponent.Child.Auth -> AuthContent(child.component, topInset, bottomInset)
                is RootComponent.Child.Library -> NavigationContent(child.component, windowWidth, topInset, bottomInset)
            }
        }
    }
}
package com.adwi.shoppe

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.adwi.shoppe.feature.root.RootComponent
import com.adwi.shoppe.ui.compose.RootContent
import com.adwi.shoppe.ui.compose.ShoppeTheme
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.factory

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
class MainActivity : AppCompatActivity(), DIAware {

    override val di by closestDI()
    private val root by factory<ComponentContext, RootComponent>()
    private lateinit var rootComponent: RootComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        rootComponent = root(defaultComponentContext())

        setContent {
            ShoppeTheme {
                ProvideWindowInsets {

                    val insets = LocalWindowInsets.current.systemBars
                    val density = Resources.getSystem().displayMetrics.density
                    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                        RootContent(
                            di = di,
                            component = rootComponent,
                            windowWidth = this.maxWidth,
                            topInset = (insets.top / density).dp,
                            bottomInset = (insets.bottom / density).dp
                        )
                    }
                }
            }
        }
    }
}
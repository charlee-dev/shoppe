package com.adwi.shoppe.ui.compose.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.shoppe.ui.compose.resources.HomeSections
import com.adwi.shoppe.ui.compose.resources.Resources

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun ShoppeBottomNav(
    modifier: Modifier = Modifier,
    items: List<HomeSections>,
    currentIndex: Int,
    onIndexSelected: (Int) -> Unit,
    shape: Shape = RoundedCornerShape(percent = 35),
    elevation: Dp = 20.dp,
    paddingValues: PaddingValues,
    backgroundColor: Color = MaterialTheme.colors.surface,
    gradientColor: Color = MaterialTheme.colors.primaryVariant,
    selectedColor: Color = MaterialTheme.colors.onBackground,
    notSelectedColor: Color = MaterialTheme.colors.secondary,
    message: String = "",
) {
    val horizontalGradientBrush = Brush.linearGradient(
        colors = listOf(gradientColor, backgroundColor)
    )

    Card(
        shape = shape,
        elevation = elevation,
        backgroundColor = Color.Transparent,
        modifier = modifier
            .height(Resources.dimens.barHeight)
            .padding(paddingValues = paddingValues),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape)
                .background(horizontalGradientBrush)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .layoutId("row")
                    .fillMaxSize()
            ) {
                items.forEach { item ->
                    val contentColor by animateColorAsState(
                        targetValue = if (currentIndex == item.index) selectedColor else notSelectedColor.copy(
                            alpha = .5f
                        ),
                        animationSpec = tween(500)
                    )
                    AnimatedVisibility(
                        visible = message.isEmpty(),
                        enter = slideInVertically() + expandVertically() + fadeIn(),
                        exit = slideOutVertically() + shrinkVertically() + fadeOut()
                    ) {
                        CustomBottomNavigationItem(
                            item = item,
                            onClick = { onIndexSelected(item.index) },
                            contentColor = contentColor,
                            modifier = Modifier
                        )
                    }
                }
            }
            AnimatedVisibility(
                visible = message.isNotEmpty(),
                enter = slideInVertically() + expandVertically() + fadeIn(),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .layoutId("snack")
                        .fillMaxSize()
                ) {
                    Text(text = "snackbarMessage")
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun CustomBottomNavigationItem(
    modifier: Modifier = Modifier,
    item: HomeSections,
    onClick: () -> Unit,
    contentColor: Color,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        IconButton(
            onClick = onClick, modifier = Modifier
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = contentColor,
                modifier = modifier.padding(12.dp)
            )
        }
    }
}
package com.adwi.shoppe.ui.compose.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Storefront
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adwi.shoppe.ui.compose.resources.Colors.SecondaryLight
import com.adwi.shoppe.ui.compose.resources.HomeSections
import com.adwi.shoppe.ui.compose.resources.Resources

@ExperimentalMaterialApi
@Composable
fun SideMenu(
    modifier: Modifier = Modifier,
    items: List<HomeSections>,
    currentIndex: Int,
    onIndexSelected: (Int) -> Unit,
    width: Dp = Resources.dimens.sideMenuWidth,
    visible: Boolean = true,
) {
    AnimatedVisibility(visible) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxHeight()
                .width(width)
                .padding(horizontal = 16.dp)
        ) {
            MenuAppHeader()
            items.forEach { item ->
                MenuItem(
                    text = item.name,
                    icon = item.icon,
                    isSelected = item.index == currentIndex,
                    onClick = { onIndexSelected(item.index) },
                    modifier = Modifier
                )
                Spacer(Modifier.size(16.dp))
            }
        }
    }
}

@Composable
fun MenuAppHeader(
    modifier: Modifier = Modifier,
    iconColor: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.onSurface,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(vertical = 32.dp)
    ) {
        Icon(
            imageVector = Icons.TwoTone.Storefront,
            contentDescription = "App logo",
            tint = textColor,
            modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.size(16.dp))
        Text(text = "sho", color = textColor, style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold))
        Text(text = "pp", color = iconColor, style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold))
        Text(text = "e", color = textColor, style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold))
    }
}

@ExperimentalMaterialApi
@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    shape: Shape = RoundedCornerShape(20),
    isSelected: Boolean = false,
    selectedColor: Color = MaterialTheme.colors.secondary,
    accentColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.onSecondary,
) {
    val backgroundColorState by animateColorAsState(
        targetValue = if (isSelected) selectedColor else Color.Transparent,
        animationSpec = tween(200)
    )
    val backgroundAccentColorState by animateColorAsState(
        targetValue = if (isSelected) SecondaryLight else Color.Transparent,
        animationSpec = tween(200)
    )
    val iconColorState by animateColorAsState(
        targetValue = if (isSelected) accentColor else MaterialTheme.colors.onSurface,
        animationSpec = tween(200)
    )
    val textColorState by animateColorAsState(
        targetValue = if (isSelected) contentColor else MaterialTheme.colors.onSurface,
        animationSpec = tween(200)
    )

    Surface(
        onClick = onClick,
        shape = shape,
        color = backgroundColorState,
        indication = null,
        modifier = modifier
            .fillMaxWidth(.8f)
            .height(50.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Surface(
                shape = shape,
                color = backgroundAccentColorState,
                modifier = Modifier
                    .size(46.dp)
                    .padding(start = 2.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Icon(
                        imageVector = icon,
                        contentDescription = text,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            Spacer(Modifier.size(24.dp))
            Text(
                text = text,
                fontSize = 12.sp,
                letterSpacing = 1.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Normal,
                color = textColorState,
                modifier = modifier
            )
            Spacer(Modifier.weight(1f))
        }
    }
}
package com.adwi.shoppe.ui.compose.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Upcoming
import androidx.compose.material.icons.twotone.Shop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.shoppe.feature.dashboard.DashboardComponent
import com.adwi.shoppe.ui.compose.resources.Resources

private val panelHeight = 200.dp
private val panelWidth = 300.dp

@ExperimentalMaterialApi
@Composable
fun DashboardShopsPanel(
    modifier: Modifier = Modifier,
    items: List<DashboardComponent.ShopItem>,
    onShopClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        PanelHeader(
            text = "Your shops",
            modifier = Modifier.padding(start = Resources.dimens.paddingValues)
        )
        ShoppeSpacer()
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = modifier
        ) {
            item {
                if (items.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(panelHeight)
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(32.dp)
                        )
                    }
                }
            }
            items(items = items) { item ->
                DashboardShopPanelItem(
                    shop = item,
                    onShopClick = { onShopClick(item.id) }
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DashboardShopPanelItem(
    modifier: Modifier = Modifier,
    shop: DashboardComponent.ShopItem,
    onShopClick: () -> Unit,
    shape: Shape = Resources.shapes.medium,
    elevation: Dp = 0.dp,
) {
    Surface(
        onClick = onShopClick,
        shape = shape,
        elevation = elevation,
        color = MaterialTheme.colors.background,
        modifier = modifier
            .height(panelHeight)
            .width(panelWidth)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight()
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.Shop,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    ShoppeSpacer()
                    Text(
                        text = shop.name,
                        fontWeight = FontWeight.Light
                    )
                }
            }
            Surface(
                color = MaterialTheme.colors.secondary,
                shape = shape,
                modifier = Modifier
                    .padding(4.dp)
                    .weight(2f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Surface(
                        color = MaterialTheme.colors.primary,
                        shape = shape,
                        modifier = Modifier
                            .fillMaxHeight(.66f)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.align(Alignment.Center)
                            ) {
//                                Text(text = "Orders")
                                DashboardPanelOrderMessageWithIcon(
                                    icon = Icons.Default.SentimentVerySatisfied,
                                    iconColor = MaterialTheme.colors.onPrimary,
                                    value = shop.totalOrders,
                                    valueColor = MaterialTheme.colors.onPrimary,
                                    modifier = Modifier
                                )
                                DashboardPanelOrderMessageWithIcon(
                                    icon = Icons.Default.Upcoming,
                                    iconColor = MaterialTheme.colors.onPrimary,
                                    value = shop.upcomingOrders,
                                    valueColor = MaterialTheme.colors.onPrimary,
                                    modifier = Modifier
                                )
                            }
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    DashboardPanelOrderMessageWithIcon(
                        icon = Icons.Default.Star,
                        iconColor = Resources.colors.Gold,
                        value = shop.rating,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun DashboardPanelOrderMessageWithIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    value: Int,
    iconColor: Color,
    valueColor: Color = MaterialTheme.colors.onSurface,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            tint = iconColor,
            modifier = Modifier
        )
        ShoppeSpacer(16)
        Text(
            text = value.toString(),
            color = valueColor,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
            modifier = Modifier
        )
    }
}
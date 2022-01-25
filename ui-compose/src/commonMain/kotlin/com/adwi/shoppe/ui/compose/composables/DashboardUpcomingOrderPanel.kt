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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.outlined.Upcoming
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.shoppe.feature.upcomingorders.UpcomingOrdersComponent
import com.adwi.shoppe.ui.compose.resources.Resources

private val orderPanelHeight = 100.dp
private val orderPanelWight = 200.dp

@ExperimentalMaterialApi
@Composable
fun DashboardUpcomingOrderPanel(
    modifier: Modifier = Modifier,
    items: List<UpcomingOrdersComponent.OrderItem>,
    onOrderClick: (String) -> Unit,
    isLoading: Boolean,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        PanelHeader(
            text = "Upcoming orders",
            modifier = Modifier.padding(start = Resources.dimens.paddingValues)
        )
        ShoppeSpacer()
        if (items.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = modifier
            ) {
                items(items = items) { item ->
                    DashboardOrderPanelItem(
                        order = item,
                        onShopClick = { onOrderClick(item.id) }
                    )
                }
            }
        }
        if (items.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(orderPanelHeight)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(32.dp)
                    )
                } else {
                    Text(
                        text = "No upcoming orders",
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DashboardOrderPanelItem(
    modifier: Modifier = Modifier,
    order: UpcomingOrdersComponent.OrderItem,
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
            .height(orderPanelHeight)
            .width(orderPanelWight)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircleIcon()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                Text(
                    text = order.serviceName,
                    fontWeight = FontWeight.Light
                )
            }
            Spacer(Modifier.weight(2f))
            Surface(
                color = MaterialTheme.colors.secondary,
                shape = shape,
                modifier = Modifier
                    .padding(4.dp)
                    .weight(2f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = order.quantity.toString(),
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = "x",
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = order.price.toString(),
                        fontWeight = FontWeight.Light
                    )
                }
            }
            Spacer(Modifier.weight(1f))
            Surface(
                color = MaterialTheme.colors.primary,
                shape = shape,
                modifier = Modifier
                    .fillMaxHeight(.66f)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    DashboardPanelOrderMessageWithIcon(
                        icon = Icons.Default.Money,
                        iconColor = MaterialTheme.colors.onPrimary,
                        value = order.totalPrice().toString(),
                        valueColor = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun CircleIcon(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    icon: ImageVector = Icons.Outlined.Upcoming,
) {
    Surface(
        shape = shape,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = modifier.padding(4.dp)
        )
    }
}
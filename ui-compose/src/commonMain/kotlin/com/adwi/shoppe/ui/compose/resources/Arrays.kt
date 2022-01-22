package com.adwi.shoppe.ui.compose.resources

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.ManageAccounts
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

object Arrays {

    val navSections = arrayOf(
        "Dashboard",
        "Manager",
        "Planner",
        "Settings",
    )

    val shelfIcons = arrayOf(
        Icons.Rounded.Dashboard,
        Icons.Rounded.ManageAccounts,
        Icons.Rounded.Schedule,
        Icons.Rounded.Settings,
    )

    val periods = arrayOf(
        "All time",
        "Last 365 days",
        "Last 180 days",
        "Last 90 days",
        "Last 30 days",
        "Last 7 days"
    )

    val profileMenu = arrayOf(
        "Settings",
        "Log Out"
    )
}

enum class HomeSections(
    title: String,
    val icon: ImageVector,
    val index: Int,
) {
    DASHBOARD("Dashboard", Icons.Outlined.Dashboard, 0),
    MANAGER("Manager", Icons.Outlined.ManageAccounts, 1),
    PLANNER("Planner", Icons.Outlined.Task, 2),
    SETTINGS("Settings", Icons.Outlined.AccountCircle, 3)
}
package com.adwi.shoppe.ui.compose.resources

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.ManageAccounts
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Settings

object Arrays {

    val shelves = arrayOf(
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
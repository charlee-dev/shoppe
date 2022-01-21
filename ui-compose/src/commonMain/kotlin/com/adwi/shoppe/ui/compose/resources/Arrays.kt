package com.adwi.shoppe.ui.compose.resources

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Album
import androidx.compose.material.icons.rounded.Audiotrack
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.History

object Arrays {

    val shelves = arrayOf(
        "Resents",
        "Artists",
        "Albums",
        "Tracks",
        "Profile"
    )

    val shelfIcons = arrayOf(
        Icons.Rounded.History,
        Icons.Rounded.Face,
        Icons.Rounded.Album,
        Icons.Rounded.Audiotrack,
        Icons.Rounded.AccountCircle
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
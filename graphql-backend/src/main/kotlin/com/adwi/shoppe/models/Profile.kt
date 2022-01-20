package com.adwi.shoppe.models

data class Profile(
    val user: User,
    val shops: List<Shop> = emptyList(),
)
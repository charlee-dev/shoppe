package com.adwi.shoppe.models

data class User(
    override val id: String,
    val email: String,
    val hashedPass: ByteArray,
//    override var address: Address? = null,
    var orders: List<Order> = emptyList(),
) : Model

data class UserInput(
    val email: String,
    val password: String,
//    var address: Address?,
)

data class UserResponse(
    val token: String,
    val user: User,
//    var address: Address?,
)
package com.adwi.shoppe.models

data class Service(
    override var id: String,
    val shopId: String,
    var name: String,
    var description: String,
    var price: Double,
    var duration: Int,
) : Model

data class ServiceInput(
    var name: String,
    var description: String,
    var price: Double,
    var duration: Int,
)

data class ServicePage(
    val results: List<Service>,
    val info: PagingInfo,
)

package com.adwi.shoppe.data.local.mapper

import com.adwi.kotlin.data.local.Service
import com.adwi.shoppe.data.remote.CreateServiceMutation
import com.adwi.shoppe.data.remote.GetServiceQuery
import com.adwi.shoppe.data.remote.GetShopByIdQuery
import com.adwi.shoppe.data.remote.ServicePagedByShopIdQuery
import com.adwi.shoppe.data.remote.UpdateServiceMutation

fun GetServiceQuery.GetService.toService() = Service(
    id = id,
    shopId = shopId,
    name = name,
    description = description,
    price = price,
    duration = duration.toLong()
)

fun ServicePagedByShopIdQuery.Result.toService() = Service(
    id = id,
    shopId = shopId,
    name = name,
    description = description,
    price = price,
    duration = duration.toLong()
)

fun GetShopByIdQuery.Service.toService() = Service(
    id = id,
    shopId = shopId,
    name = name,
    description = description,
    price = price,
    duration = duration.toLong()
)

fun CreateServiceMutation.CreateService.toService() = Service(
    id = id,
    shopId = shopId,
    name = name,
    description = description,
    price = price,
    duration = duration.toLong()
)

fun UpdateServiceMutation.UpdateService.toService() = Service(
    id = id,
    shopId = shopId,
    name = name,
    description = description,
    price = price,
    duration = duration.toLong()
)
package com.adwi.shoppe.graphql

import com.adwi.shoppe.models.Service
import com.adwi.shoppe.models.ServiceInput
import com.adwi.shoppe.services.ServiceService
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder

fun SchemaBuilder.serviceSchema(serviceService: ServiceService) {

    query("getService") {
        description = "Get an existing service"
        resolver { serviceId: String ->
            try {
                serviceService.getService(serviceId)
            } catch (e: Exception) {
                null
            }
        }
    }

    query("servicePagedByShopId") {
        description = "Retrieve services page"
        resolver { shopId: String, page: Int?, size: Int? ->
            try {
                serviceService.getServicesPageByShopId(shopId, page ?: 0, size ?: 10)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("createService") {
        description = "Create a new service"
        resolver { shopId: String, serviceInput: ServiceInput ->
            try {
                serviceService.createService(shopId, serviceInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("updateService") {
        description = "Update an existing service"
        resolver { serviceId: String, shopId: String, serviceInput: ServiceInput ->
            try {
                serviceService.updateService(serviceId, shopId, serviceInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("deleteService") {
        description = "Delete a service"
        resolver { serviceId: String ->
            try {
                serviceService.deleteService(serviceId)
            } catch (e: Exception) {
                null
            }
        }
    }

    inputType<ServiceInput> {
        description = "The input of the service without the identifier"
    }

    type<Service> {
        description = "Service object with the attributes text and rating"
    }
}
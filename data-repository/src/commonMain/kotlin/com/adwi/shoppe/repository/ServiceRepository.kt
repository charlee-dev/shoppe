package com.adwi.shoppe.repository

import com.adwi.kotlin.data.local.Service
import com.adwi.shoppe.CreateServiceMutation
import com.adwi.shoppe.DeleteServiceMutation
import com.adwi.shoppe.GetServiceQuery
import com.adwi.shoppe.UpdateServiceMutation
import com.adwi.shoppe.data.api.ApolloProvider
import com.adwi.shoppe.data.local.mapper.toService
import com.adwi.shoppe.type.ServiceInput
import com.apollographql.apollo3.annotations.ApolloExperimental
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ApolloExperimental
class ServiceRepository(apolloProvider: ApolloProvider) : BaseRepository(apolloProvider) {

    suspend fun getService(serviceId: String): Service? {
        val response = apolloClient.query(GetServiceQuery(serviceId)).execute()
        return response.data?.getService?.toService()
    }

    suspend fun createService(shopId: String, serviceInput: ServiceInput): Service? {
        val response = apolloClient.mutation(CreateServiceMutation(shopId, serviceInput)).execute()
        return response.data?.createService?.toService()
    }

    suspend fun updateService(
        serviceId: String,
        shopId: String,
        serviceInput: ServiceInput,
    ): Service? {
        val response =
            apolloClient.mutation(UpdateServiceMutation(serviceId, shopId, serviceInput)).execute()
        return response.data?.updateService?.toService()
    }

    suspend fun deleteService(serviceId: String): Boolean? {
        val response = apolloClient.mutation(DeleteServiceMutation(serviceId)).execute()
        return response.data?.deleteService
    }
}
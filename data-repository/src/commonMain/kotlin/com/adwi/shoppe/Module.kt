package com.adwi.shoppe

import com.adwi.shoppe.repository.AuthRepository
import com.adwi.shoppe.repository.ReviewRepository
import com.adwi.shoppe.repository.ServiceRepository
import com.adwi.shoppe.repository.ShopOrderRepository
import com.adwi.shoppe.repository.ShopRepository
import com.apollographql.apollo3.annotations.ApolloExperimental
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

@ApolloExperimental
@ExperimentalCoroutinesApi
val repositoryModule = DI.Module("repository") {
    bindSingleton { AuthRepository(instance()) }
    bindSingleton { ShopRepository(instance()) }
    bindSingleton { ReviewRepository(instance()) }
    bindSingleton { ServiceRepository(instance()) }
    bindSingleton { ShopOrderRepository(instance()) }
}
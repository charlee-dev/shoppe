package com.adwi.shoppe.data

import com.adwi.shoppe.data.api.ApolloProvider
import com.adwi.shoppe.data.local.DatabaseDriverFactory
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val datasourceDataModule = DI.Module("datasourceModule") {

    bindSingleton { DatabaseDriverFactory(di) }
    bindSingleton { ApolloProvider(instance()) }
}

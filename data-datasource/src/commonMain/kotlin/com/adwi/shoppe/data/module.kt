package com.adwi.shoppe.data

import com.adwi.shoppe.data.api.ApolloProvider
import com.adwi.shoppe.data.local.DatabaseDriverFactory
import com.adwi.shoppe.data.local.ShoppeDatabase
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val datasourceDataModule = DI.Module("datasourceModule") {

    bindSingleton { DatabaseDriverFactory(di).create() }
    bindSingleton { ShoppeDatabase(instance()) }

    bindSingleton { instance<ShoppeDatabase>().orderQueries }
    bindSingleton { instance<ShoppeDatabase>().reviewQueries }
    bindSingleton { instance<ShoppeDatabase>().serviceQueries }
    bindSingleton { instance<ShoppeDatabase>().shopQueries }
    bindSingleton { instance<ShoppeDatabase>().userstateQueries }

    bindSingleton { ApolloProvider(instance()) }
}

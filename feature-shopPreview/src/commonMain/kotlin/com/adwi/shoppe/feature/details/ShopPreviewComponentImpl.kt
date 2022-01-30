package com.adwi.shoppe.feature.details

import com.adwi.shoppe.feature.details.store.ShopPreviewStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.direct
import org.kodein.di.instance

@ExperimentalCoroutinesApi
class ShopPreviewComponentImpl(
    override val di: DI,
    private val componentContext: ComponentContext,
    shopId: String,
    onSaveClick: (Boolean) -> Unit,
    navigateBack: () -> Unit,
) : ShopPreviewComponent, DIAware, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        ShopPreviewStoreFactory(
            storeFactory = direct.instance(),
            shopRepository = direct.instance(),
            shopId = shopId,
            onSaveClick = { isSaved -> onSaveClick(isSaved) },
            navigateBack = navigateBack
        ).create()
    }

    override val model: Flow<ShopPreviewComponent.Model> = store.states.map {
        ShopPreviewComponent.Model(
            shop = it.shop,
            isRefreshing = it.isRefreshing
        )
    }
}
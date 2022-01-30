package com.adwi.shoppe.feature.details

import co.touchlab.kermit.Logger
import com.adwi.shoppe.feature.details.store.ShopPreviewStore
import com.adwi.shoppe.feature.details.store.ShopPreviewStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
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

    private val router = router<Config, ShopPreviewComponent.Child>(
        initialConfiguration = Config.Preview,
        handleBackButton = true
    ) { configuration, componentContext ->
        Logger.v("router $configuration")
        when (configuration) {
            is Config.Preview -> ShopPreviewComponent.Child.Preview
            is Config.Edit -> ShopPreviewComponent.Child.Edit(configuration.shopId)
            is Config.Add -> ShopPreviewComponent.Child.Add(configuration.shopId)
        }
    }

    override val routerState: Value<RouterState<*, ShopPreviewComponent.Child>> = router.state

    private val store = instanceKeeper.getStore {
        ShopPreviewStoreFactory(storeFactory = direct.instance(),
            shopRepository = direct.instance(),
            shopId = shopId,
            onSaveClick = { isSaved -> onSaveClick(isSaved) },
            navigateBack = navigateBack).create()
    }

    override val model: Flow<ShopPreviewComponent.Model> = store.states.map {
        ShopPreviewComponent.Model(shop = it.shop, isRefreshing = it.isRefreshing)
    }

    override fun saveEdit(id: String, name: String, description: String, imageUrl: String) {
        store.accept(ShopPreviewStore.Intent.UpdateShop(id, name, description, imageUrl))
    }

    override fun saveAdd(name: String, description: String, imageUrl: String) {
        store.accept(ShopPreviewStore.Intent.CreateShop(name, description, imageUrl))
    }

    private sealed class Config : Parcelable {
        @Parcelize
        object Preview : Config()

        @Parcelize
        data class Edit(val shopId: String) : Config()

        @Parcelize
        data class Add(val shopId: String) : Config()
    }
}
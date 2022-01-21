package com.adwi.shoppe.ui.compose.native

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toComposeImageBitmap
import com.adwi.shoppe.utils.AppCoroutineDispatcher
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.runBlocking
import org.jetbrains.skia.Image
import org.kodein.di.compose.localDI
import org.kodein.di.direct
import org.kodein.di.instance

@Composable
actual fun remoteImagePainter(
    url: String,
): Painter {

    val client = localDI().direct.instance<HttpClient>()

    val bitmap = runBlocking(AppCoroutineDispatcher.IO) {
        try {
            Image.makeFromEncoded(client.get<ByteArray>(url)).toComposeImageBitmap()
        } catch (e: Exception) {
            ImageBitmap(1, 1)
        }
    }

    return BitmapPainter(bitmap)
}
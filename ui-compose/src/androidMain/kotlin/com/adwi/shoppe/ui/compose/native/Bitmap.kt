package com.adwi.shoppe.ui.compose.native

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.adwi.shoppe.utils.Bitmap

actual fun Bitmap?.asComposeBitmap(): ImageBitmap? = this?.asImageBitmap()

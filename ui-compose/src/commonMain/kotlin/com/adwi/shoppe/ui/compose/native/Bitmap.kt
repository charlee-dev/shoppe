package com.adwi.shoppe.ui.compose.native

import androidx.compose.ui.graphics.ImageBitmap
import com.adwi.shoppe.utils.Bitmap

expect fun Bitmap?.asComposeBitmap(): ImageBitmap?
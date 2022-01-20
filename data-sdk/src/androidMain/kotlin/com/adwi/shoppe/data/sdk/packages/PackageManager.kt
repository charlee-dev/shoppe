package com.adwi.shoppe.data.sdk.packages

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import com.adwi.shoppe.utils.AppCoroutineDispatcher
import kotlinx.coroutines.withContext
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

actual class PackageManager actual constructor(override val di: DI) : DIAware {

    private val context: Context by instance()

    @SuppressLint("QueryPermissionsNeeded")
    actual suspend fun getApps() = withContext(AppCoroutineDispatcher.IO) {

        context.packageManager
            .queryIntentActivities(
                Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER),
                PackageManager.GET_META_DATA
            )
            .map { it.activityInfo.applicationInfo }
            .filter { it.flags != ApplicationInfo.FLAG_SYSTEM }
            .map {
                Package(
                    name = it.packageName,
                    label = it.loadLabel(context.packageManager).toString(),
                    bitmap = with(it.loadIcon(context.packageManager)) {

                        val bitmap = Bitmap.createBitmap(
                            intrinsicWidth,
                            intrinsicHeight,
                            Bitmap.Config.ARGB_8888
                        )

                        val canvas = Canvas(bitmap)

                        setBounds(0, 0, canvas.width, canvas.height)
                        draw(canvas)

                        bitmap
                    }
                )
            }
    }
}
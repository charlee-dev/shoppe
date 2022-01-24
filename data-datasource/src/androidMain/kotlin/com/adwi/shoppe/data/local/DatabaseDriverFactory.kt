package com.adwi.shoppe.data.local

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import com.adwi.shoppe.data.ShoppeDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

actual class DatabaseDriverFactory actual constructor(override val di: DI): DIAware {

    private val context: Context by di.instance()

    actual fun create(): SqlDriver = AndroidSqliteDriver(
        schema = ShoppeDatabase.Schema,
        context = context,
        name = "shoppe.db",
        callback = object : AndroidSqliteDriver.Callback(ShoppeDatabase.Schema) {
            override fun onConfigure(db: SupportSQLiteDatabase) {
                super.onConfigure(db)
                db.setForeignKeyConstraintsEnabled(true)
            }
        }
    )
}
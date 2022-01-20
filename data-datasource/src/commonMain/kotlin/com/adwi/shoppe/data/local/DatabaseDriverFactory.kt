package com.adwi.shoppe.data.local

import com.squareup.sqldelight.db.SqlDriver
import org.kodein.di.DI
import org.kodein.di.DIAware

expect class DatabaseDriverFactory(di: DI): DIAware {
    fun create(): SqlDriver
}
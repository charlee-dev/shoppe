package com.adwi.shoppe

import com.adwi.shoppe.di.configureKodein
import com.adwi.shoppe.plugins.configureContentNegotiations
import com.adwi.shoppe.plugins.configureGraphQL
import com.adwi.shoppe.plugins.configureRouting
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

val port = System.getenv("PORT")?.toInt() ?: 8080

fun main() {
    embeddedServer(Netty, port) {
        configureKodein()
        configureGraphQL()
        configureContentNegotiations()
        configureRouting()
    }.start(wait = true)
}
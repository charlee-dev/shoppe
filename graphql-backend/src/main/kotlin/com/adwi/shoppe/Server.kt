package com.adwi.shoppe

import com.adwi.shoppe.di.configureKodein
import com.adwi.shoppe.plugins.configureContentNegotiations
import com.adwi.shoppe.plugins.configureGraphQL
import com.adwi.shoppe.plugins.configureRouting
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

val port = System.getenv("PORT")?.toInt() ?: 8080
val mongoKey =
    "mongodb+srv://shoppe:1n1ezm1en1as1en1c@shoppe.9q104.mongodb.net/myFirstDatabase?retryWrites=true&w=majority"

fun main() {
    embeddedServer(Netty, port) {
        configureKodein()
        configureGraphQL()
        configureContentNegotiations()
        configureRouting()
    }.start(wait = true)
}
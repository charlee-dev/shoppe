plugins {
    application
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
}

group = "com.adwi.ktor"
version = "0.0.1"

kotlin.sourceSets["main"].kotlin.srcDirs("src")
sourceSets["main"].resources.srcDirs("resources")

application {
    mainClassName = "com.adwi.shoppe.ServerKt"
}

tasks.withType<Jar> {
    manifest {
        attributes(
            mapOf(
                "Main-Class" to application.mainClassName
            )
        )
    }
}

dependencies {
    with(Libs.Ktor.Server) {
        implementation(core)
        implementation(netty)
        implementation(gson)
        implementation(auth)
        implementation(jwt)
        implementation(test)
    }
    with(Libs.Backend) {
        implementation(logback)
        implementation(kMongo)
        implementation(kGraphQL)
        implementation(kGraphQLKtor)
        implementation(bCrypt)
    }
    with(Libs.Kodein) {
        implementation(server)
    }
}

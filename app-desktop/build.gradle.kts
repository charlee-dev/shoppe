import org.jetbrains.compose.compose

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose") version 1.2.0
}

dependencies {

    implementation(project(Module.UI.compose))
    implementation(project(Module.Feature.root))

    implementation(compose.desktop.currentOs)

    implementation(Libs.Kodein.common)
    implementation(Libs.ArkIvanov.Decompose.common)
    implementation(Libs.ArkIvanov.Decompose.compose)
}

compose.desktop {
    application {
        mainClass = "com.adwi.shoppe.MainKt"
    }
}
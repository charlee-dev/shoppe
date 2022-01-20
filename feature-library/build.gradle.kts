plugins {
    id("shoppe-component-mvi")
    id("kotlin-parcelize")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(Libs.ArkIvanov.Essenty.parcelable)
            }
        }
    }
}
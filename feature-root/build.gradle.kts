plugins {
    id("shoppe-component-mvi")
    id("kotlin-parcelize")
}

kotlin {
    sourceSets {

        named("commonMain") {
            dependencies {

                api(project(Module.Data.sdk))
                api(project(Module.Data.repository))

                implementation(project(Module.Feature.auth))
                implementation(project(Module.Feature.navigation))
                implementation(project(Module.Feature.dashboard))

                implementation(Libs.ArkIvanov.MVIKotlin.main)
                implementation(Libs.ArkIvanov.Essenty.parcelable)
            }
        }
    }
}
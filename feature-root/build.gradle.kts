plugins {
    id("shoppe-component-mvi")
    id("kotlin-parcelize")
}

kotlin {
    sourceSets {

        named("commonMain") {
            dependencies {
                
                implementation(project(Module.Data.sdk))
                implementation(project(Module.Data.repository))

                implementation(project(Module.Feature.auth))
                implementation(project(Module.Feature.library))

                implementation(Libs.ArkIvanov.MVIKotlin.main)
                implementation(Libs.ArkIvanov.Essenty.parcelable)
            }
        }
    }
}
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

                implementation(project(Module.Feature.dashboard))
                implementation(project(Module.Feature.manager))
                implementation(project(Module.Feature.planner))
                implementation(project(Module.Feature.settings))

                implementation(Libs.ArkIvanov.MVIKotlin.main)
                implementation(Libs.ArkIvanov.Essenty.parcelable)
            }
        }
    }
}
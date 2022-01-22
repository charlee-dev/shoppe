plugins {
    id("shoppe-multiplatform")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(Module.utils))
                implementation(Libs.Kodein.common)
                api(Libs.Settings.common)
                implementation(Libs.Settings.coroutines)
                implementation(Libs.Kermit.common)
                implementation(Libs.Kotlin.coroutines)
                implementation(Libs.Kotlin.Serialization.json)
            }
        }
    }
}
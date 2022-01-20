plugins {
    id("shoppe-multiplatform")
}

kotlin {
    sourceSets {

        named("commonMain") {
            dependencies {
                api(project(Module.Data.datasource))
                implementation(Libs.Kotlin.coroutines)
                implementation(Libs.Kodein.common)
                implementation(Libs.Kermit.common)
                api(Libs.Apollo.apollo)
            }
        }

        named("androidMain") {
            dependencies {
            }
        }

        named("desktopMain") {
            dependencies {
            }
        }
    }
}

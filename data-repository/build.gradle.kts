plugins {
    id("shoppe-multiplatform")
}

kotlin {
    sourceSets {

        named("commonMain") {
            dependencies {
                implementation(project(Module.Data.datasource))
                implementation(Libs.Kotlin.coroutines)
                implementation(Libs.Kodein.common)
                implementation(Libs.Kermit.common)
                implementation(Libs.Apollo.apollo)
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

plugins {
    id("shoppe-multiplatform")
}

kotlin {
    sourceSets {

        named("commonMain") {
            dependencies {
                implementation(Libs.Kodein.common)
                implementation(Libs.ArkIvanov.Decompose.common)
                api(Libs.Kermit.common)
            }
        }
    }
}
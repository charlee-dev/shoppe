plugins {
    id("shoppe-multiplatform")
    id("org.jetbrains.compose") version Libs.AndroidX.Compose.version
}

kotlin {
    sourceSets {

        named("commonMain") {
            dependencies {

                implementation(project(Module.utils))

                with(compose) {
                    implementation(ui)
                    implementation(preview)
                    implementation(runtime)
                    implementation(material)
                    implementation(uiTooling)
                    implementation(animation)
                    implementation(foundation)
                    implementation(animationGraphics)
                    implementation(materialIconsExtended)
                }

                implementation(Libs.Kodein.compose)
                implementation(Libs.ArkIvanov.Decompose.common)
                implementation(Libs.ArkIvanov.Decompose.compose)
            }
        }

        named("androidMain") {
            dependencies {
                implementation(Libs.Accompanist.coil)
                implementation(Libs.Accompanist.swipeRefresh)
            }
        }

        named("desktopMain") {
            dependencies {
                implementation(project(Module.utils))
                implementation(Libs.Ktor.Client.Core.common)
                implementation(compose.desktop.currentOs)
            }
        }
    }
}
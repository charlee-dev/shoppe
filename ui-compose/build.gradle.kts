plugins {
    id("shoppe-multiplatform")
    id("org.jetbrains.compose") version 1.3.0
}

kotlin {
    sourceSets {

        named("commonMain") {
            dependencies {

                implementation(project(Module.utils))

                with(Module.Feature) {
                    implementation(project(root))
                    implementation(project(splash))
                    implementation(project(auth))
                    implementation(project(navigation))
                    implementation(project(dashboard))
                    implementation(project(shops))
                    implementation(project(upcomingOrders))
                    implementation(project(manager))
                    implementation(project(planner))
                    implementation(project(settings))
                    implementation(project(shopPreview))
                }

                with(compose) {
                    implementation(ui)
                    implementation(runtime)
                    api(material)
                    implementation(uiTooling)
                    api(animation)
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
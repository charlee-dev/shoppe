plugins {
    id("shoppe-component-mvi")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(Module.Data.sdk))
                implementation(project(Module.Data.repository))
            }
        }
    }
}
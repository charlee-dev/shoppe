rootProject.name = "shoppe"

include(

    ":backend",

    ":app-android",
    ":app-desktop",

    ":feature-root",

    ":data-sdk",
    ":data-datasource",
    ":data-repository",

    ":ui-compose",
    ":utils"
)

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

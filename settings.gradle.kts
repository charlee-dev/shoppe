rootProject.name = "shoppe"

include(

    ":graphql-backend",

    ":app-android",
    ":app-desktop",

    ":feature-root",
    ":feature-auth",
    ":feature-navigation",
    ":feature-dashboard",
    ":feature-manager",
    ":feature-planner",
    ":feature-settings",
    ":feature-details",

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

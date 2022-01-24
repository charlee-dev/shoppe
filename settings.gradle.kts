rootProject.name = "shoppe"

include(

    ":graphql-backend",

    ":app-android",
    ":app-desktop",

    ":feature-root",
    ":feature-splash",
    ":feature-auth",
    ":feature-navigation",
    ":feature-dashboard",
    ":feature-shops",
    ":feature-manager",
    ":feature-planner",
    ":feature-settings",
    ":feature-shopPreview",

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

rootProject.name = "shoppe"

include(

    ":graphql-backend",

    ":app-android",
    ":app-desktop",

    ":feature-root",
    ":feature-auth",
    ":feature-library",

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

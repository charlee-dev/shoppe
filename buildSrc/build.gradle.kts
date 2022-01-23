plugins {
    `kotlin-dsl`
}

repositories {
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(Libs.Kotlin.gradlePlugin)
    implementation(Libs.gradle)
}

kotlin {
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/kotlin")
}

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("shoppe-android")
}

/*
configurations {
    create("testApi")
    create("testDebugApi")
    create("testReleaseApi")
    create("androidTestApi")
    create("androidTestDebugApi")
    create("androidTestReleaseApi")
}
*/

kotlin {

    android()
    jvm("desktop")

    sourceSets {

        val androidAndroidTestRelease by getting
        val androidTest by getting {
            dependsOn(androidAndroidTestRelease)
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}
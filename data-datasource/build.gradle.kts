plugins {
    id("shoppe-multiplatform")
    id("com.squareup.sqldelight")
    id("com.apollographql.apollo3").version("3.0.0")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(Libs.Kodein.common)
                implementation(Libs.Krypto.common)
                implementation(Libs.Kermit.common)
                implementation(Libs.SqlDelight.coroutines)
                api(Libs.Apollo.apollo)
            }
        }
        named("androidMain") {
            dependencies {
                api(Libs.SqlDelight.Driver.android)
            }
        }
        named("desktopMain") {
            dependencies {
                api(Libs.SqlDelight.Driver.sqlite)
            }
        }
    }
}

sqldelight {
    database("ShoppeDatabase") {
        packageName = "com.adwi.shoppe.data.local"
    }
}

apollo {
    packageName.set("com.adwi.shoppe.data.remote")
}
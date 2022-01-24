import com.apollographql.apollo3.gradle.internal.ApolloDownloadSchemaTask

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
        packageName = "com.adwi.shoppe.data"
    }
}

apollo {
    packageName.set("com.adwi.shoppe")
}

tasks.register("downloadSchema", ApolloDownloadSchemaTask::class.java) {
    endpoint.set("https://shoppe-kmm.herokuapp.com/graphql")
    schema.set("/Users/adrianwitaszak/Projects/shoppe/data-datasource/src/commonMain/graphql/com/adwi/shoppe/data/schema.json")
}
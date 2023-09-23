object App {

    const val id = "com.adwi.shoppe"

    object Version {
        const val code = 1
        const val name = "1.0"
    }
}

object Sdk {
    object Version {
        const val min = 21
        const val target = 31
        const val compile = 31
        const val buildTools = "30.0.3"
    }
}

object Module {

    const val utils = ":utils"

    object Feature {
        const val root = ":feature-root"
        const val auth = ":feature-auth"
        const val navigation = ":feature-navigation"

        const val splash = ":feature-splash"
        const val dashboard = ":feature-dashboard"
        const val shops = ":feature-shops"
        const val upcomingOrders = ":feature-upcomingOrders"
        const val manager = ":feature-manager"
        const val planner = ":feature-planner"
        const val settings = ":feature-settings"
        const val shopPreview = ":feature-shopPreview"
        const val user = ":feature-user"

        const val top = ":feature-top"
        const val menu = ":feature-menu"
    }

    object Data {
        const val sdk = ":data-sdk"
        const val datasource = ":data-datasource"
        const val repository = ":data-repository"
    }

    object UI {
        const val compose = ":ui-compose"
    }
}

object Libs {

    const val gradle = "com.android.tools.build:gradle:7.4.0"

    object Kotlin {
        private const val version = "1.6.10"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2"
        const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:0.3.1"

        object Serialization {
            const val common = "org.jetbrains.kotlin:kotlin-serialization:$version"
            const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2"
        }
    }

    object Ktor {
        private const val version = "1.6.7"

        object Server {
            const val core = "io.ktor:ktor-server-core:$version"
            const val netty = "io.ktor:ktor-server-netty:$version"
            const val gson = "io.ktor:ktor-gson:$version"
            const val auth = "io.ktor:ktor-auth:$version"
            const val jwt = "io.ktor:ktor-auth-jwt:$version"
            const val test = "io.ktor:ktor-server-tests:$version"
        }

        object Client {
            object Core {
                const val common = "io.ktor:ktor-client-core:$version"
                const val jvm = "io.ktor:ktor-client-core-jvm:$version"
            }

            object Logging {
                const val common = "io.ktor:ktor-client-logging:$version"
                const val jvm = "io.ktor:ktor-client-logging-jvm:$version"
            }

            object Serialization {
                const val common = "io.ktor:ktor-client-serialization:$version"
                const val jvm = "io.ktor:ktor-client-serialization-jvm:$version"
            }

            object Engine {
                const val okhttp = "io.ktor:ktor-client-okhttp:$version"
            }
        }
    }

    object Backend {
        private const val logbackVersion = "1.2.10"
        private const val kGraphQLVersion = "0.17.14"
        private const val kMongoVersion = "4.4.0"
        private const val bcryptVersion = "0.9.0"
        const val logback = "ch.qos.logback:logback-classic:$logbackVersion"
        const val kMongo = "org.litote.kmongo:kmongo:$kMongoVersion"
        const val kGraphQL = "com.apurebase:kgraphql:$kGraphQLVersion"
        const val kGraphQLKtor = "com.apurebase:kgraphql-ktor:$kGraphQLVersion"
        const val bCrypt = "at.favre.lib:bcrypt:$bcryptVersion"
    }

    object ArkIvanov {

        object MVIKotlin {
            private const val version = "3.0.0-alpha03"
            const val common = "com.arkivanov.mvikotlin:mvikotlin:$version"
            const val main = "com.arkivanov.mvikotlin:mvikotlin-main:$version"
            const val coroutines = "com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:$version"
        }

        object Decompose {
            private const val version = "0.5.0"
            const val common = "com.arkivanov.decompose:decompose:$version"
            const val jvm = "com.arkivanov.decompose:decompose-jvm:$version"
            const val compose = "com.arkivanov.decompose:extensions-compose-jetbrains:$version"
        }

        object Essenty {
            private const val version = "0.2.2"
            const val instanceKeeper = "com.arkivanov.essenty:instance-keeper:$version"
            const val parcelable = "com.arkivanov.essenty:parcelable:$version"
        }
    }

    object SqlDelight {
        private const val version = "1.5.3"
        const val gradlePlugin = "com.squareup.sqldelight:gradle-plugin:$version"
        const val coroutines = "com.squareup.sqldelight:coroutines-extensions:$version"

        object Driver {
            const val android = "com.squareup.sqldelight:android-driver:$version"
            const val sqlite = "com.squareup.sqldelight:sqlite-driver:$version"
        }
    }

    object Apollo {
        private const val version = "3.0.0"
        const val apollo = "com.apollographql.apollo3:apollo-runtime:$version"
    }

    object Kodein {
        private const val version = "7.10.0"
        const val common = "org.kodein.di:kodein-di:$version"
        const val compose = "org.kodein.di:kodein-di-framework-compose:$version"
        const val android = "org.kodein.di:kodein-di-framework-android-core:$version"

        const val server = "org.kodein.di:kodein-di-framework-ktor-server-jvm:$version"
        const val genericJvm = "org.kodein.di:kodein-di-generic-jvm:$version"
    }

    object Kermit {
        private const val version = "1.0.0"
        const val common = "co.touchlab:kermit:$version"
    }

    object GradleVersions {
        const val version = "0.39.0"
        const val plugin = "com.github.ben-manes.versions"
    }

    object Krypto {
        private const val version = "2.4.7"
        const val common = "com.soywiz.korlibs.krypto:krypto:$version"
        const val android = "com.soywiz.korlibs.krypto:krypto-android:$version"
    }

    object Settings {
        private const val version = "0.8.1"
        const val common = "com.russhwolf:multiplatform-settings:$version"
        const val coroutines = "com.russhwolf:multiplatform-settings-coroutines:$version"
    }

    object AndroidX {

        const val core = "androidx.core:core-ktx:1.7.0"
        const val browser = "androidx.browser:browser:1.4.0"
        const val appCompat = "androidx.appcompat:appcompat:1.4.0"

        object Compose {
            const val version = "1.1.0-alpha1-dev550"
            const val activity = "androidx.activity:activity-compose:1.3.1"
        }
    }

    object Accompanist {
        private const val version = "0.15.0"
        const val coil = "com.google.accompanist:accompanist-coil:$version"
        const val insets = "com.google.accompanist:accompanist-insets:$version"
        const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$version"
    }

    object Shadow {
        private const val version = "7.1.2"
        const val shadow = "gradle.plugin.com.github.johnrengelman:shadow:$version"
    }
}

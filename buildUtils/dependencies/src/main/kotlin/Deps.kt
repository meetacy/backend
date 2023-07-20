@file:Suppress("FunctionName", "unused")

object Deps {
    const val compileSdkVersion = 31
    const val minSdkVersion = 21
    
    const val kotlinVersion = "1.9.0"
    private const val coroutinesVersion = "1.6.0"
    private const val serializationVersion = "1.3.2"
    private const val nodejsExternalsVersion = "0.0.7"
    private const val ktorVersion = "2.2.3"
    private const val rsocketVersion = "0.15.4"
    private const val ktorOpenapiVersion = "0.2-beta.20"
    private const val exposedVersion = "0.41.1"
    private const val datetimeVersion = "0.4.0"

    private const val postgresqlVersion = "42.2.24"
    private const val slf4jJVersion = "1.7.32"
    private const val logbackVersion = "1.2.6"
    private const val sshVersion = "2.10.1"
    
    private const val materialVersion = "1.5.0-alpha03"
    private const val recyclerViewVersion = "1.3.0-alpha01"
    private const val swipeRefreshLayoutVersion = "1.2.0"
    private const val constraintLayoutVersion = "2.1.0"
    private const val lifecycleVersion = "2.3.1"
    private const val glideVersion = "4.12.0"
    private const val androidAppCompatVersion = "1.3.1"
    private const val androidComposeVersion = "1.1.0-alpha04"
    private const val androidGradlePluginVersion = "4.2.2"

    private const val kvisionVersion = "5.1.1"
    private const val shadowVer = "7.0.0"

    private const val wdaterVersion = "0.0.2"
    private const val meetacySdkVersion = "0.0.58"
    private const val kdsVer = "1.1.0"
    private const val scriptKtVer = "0.0.2"

    object Libs {

        object KotlinGang {
            object KDS {
                const val FileDataStorage = "fun.kotlingang.kds:json-files:$kdsVer"
                const val LocalDataStorage = "fun.kotlingang.kds:json-files:$kdsVer"
                const val BundleDataStorage = "fun.kotlingang.kds:json-bundle:$kdsVer"
                object Integrations {
                    const val Androidx = "fun.kotlingang.kds:extensions-androidx:$kdsVer"
                    const val Coroutines = "fun.kotlingang.kds:extensions-coroutines:$kdsVer"
                    const val KVision = "fun.kotlingang.kds:extensions-kvision:$kdsVer"
                }
                const val Core = "fun.kotlingang.kds:core:$kdsVer"
                const val Json = "fun.kotlingang.kds:json:$kdsVer"
            }
            object ScriptKt {
                const val Wrapper = "fun.kotlingang.scriptkt:scriptkt:$scriptKtVer"
                const val ImportScript = "fun.kotlingang.scriptkt:import-script:$scriptKtVer"
                const val MavenResolver = "fun.kotlingang.scriptkt:maven-resolver:$scriptKtVer"
            }
        }

        object KVision {
            const val Core = "io.kvision:kvision:$kvisionVersion"

            object Bootstrap {
                const val Core = "io.kvision:kvision-bootstrap:$kvisionVersion"
                const val Css = "io.kvision:kvision-bootstrap-css:$kvisionVersion"
                const val DateTime = "io.kvision:kvision-bootstrap-datetime:$kvisionVersion"
                const val Select = "io.kvision:kvision-bootstrap-select:$kvisionVersion"
                const val Spinner = "io.kvision:kvision-bootstrap-spinner:$kvisionVersion"
                const val Upload = "io.kvision:kvision-bootstrap-upload:$kvisionVersion"
                const val Dialog = "io.kvision:kvision-bootstrap-dialog:$kvisionVersion"
                const val Typeahead = "io.kvision:kvision-bootstrap-typeahead:$kvisionVersion"
            }

            const val FontAwesome = "io.kvision:kvision-fontawesome:$kvisionVersion"
            const val i18n = "io.kvision:kvision-i18n:$kvisionVersion"
            const val RichText = "io.kvision:kvision-richtext:$kvisionVersion"
            const val Handlebars = "io.kvision:kvision-handlebars:$kvisionVersion"
            const val DataContainer = "io.kvision:kvision-datacontainer:$kvisionVersion"
            const val Chart = "io.kvision:kvision-chart:$kvisionVersion"
            const val Tabulator = "io.kvision:kvision-tabulator:$kvisionVersion"
            const val Pace = "io.kvision:kvision-pace:$kvisionVersion"
            const val Toast = "io.kvision:kvision-toast:$kvisionVersion"
            const val React = "io.kvision:kvision-react:$kvisionVersion"
            const val Navigo = "io.kvision:kvision-routing-navigo:$kvisionVersion"
            const val State = "io.kvision:kvision-state:$kvisionVersion"
            const val Rest = "io.kvision:kvision-rest:$kvisionVersion"
        }

        object Kotlinx {
            const val Coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
            const val CoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion"
            const val Serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion"
            const val Datetime = "org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion"
            const val Nodejs = "org.jetbrains.kotlinx:kotlinx-nodejs:$nodejsExternalsVersion"
        }
        object Ktor {
            object Client {
                const val Core = "io.ktor:ktor-client-core:$ktorVersion"
                const val Cio = "io.ktor:ktor-client-cio:$ktorVersion"
                const val Mock = "io.ktor:ktor-client-mock:$ktorVersion"
                const val ContentNegotiation = "io.ktor:ktor-client-content-negotiation:$ktorVersion"
                const val SerializationJson = "io.ktor:ktor-serialization-kotlinx-json:$ktorVersion"
                const val Logging = "io.ktor:ktor-client-logging:$ktorVersion"
                const val Curl = "io.ktor:ktor-client-curl:$ktorVersion"
            }
            object Server {
                const val Core = "io.ktor:ktor-server-core:$ktorVersion"
                const val Cio = "io.ktor:ktor-server-cio:$ktorVersion"
                const val SerializationJson = "io.ktor:ktor-serialization-kotlinx-json:$ktorVersion"
                const val RequestValidation = "io.ktor:ktor-server-request-validation:$ktorVersion"
                const val ServerStatusPage = "io.ktor:ktor-server-status-pages:$ktorVersion"
                const val ContentNegotiation = "io.ktor:ktor-server-content-negotiation:$ktorVersion"
                const val Testing = "io.ktor:ktor-server-test-host:$ktorVersion"
                const val Netty = "io.ktor:ktor-server-netty:$ktorVersion"
                const val PartialContent = "io.ktor:ktor-server-partial-content:$ktorVersion"
                const val AutoHead = "io.ktor:ktor-server-auto-head-response:$ktorVersion"
                const val Cors = "io.ktor:ktor-server-cors:$ktorVersion"
                const val Swagger = "io.ktor:ktor-server-swagger:$ktorVersion"
                const val RSocket = "io.rsocket.kotlin:rsocket-ktor-server:$rsocketVersion"
                const val WebSockets = "io.ktor:ktor-server-websockets:$ktorVersion"
                const val StatusPages = "io.ktor:ktor-server-status-pages:$ktorVersion"
            }
        }
        object Exposed {
            const val Core = "org.jetbrains.exposed:exposed-core:$exposedVersion"
            const val Jdbc = "org.jetbrains.exposed:exposed-jdbc:$exposedVersion"
            const val Time = "org.jetbrains.exposed:exposed-java-time:$exposedVersion"
        }
        object Meetacy {
            object Wdater {
                const val Core = "app.meetacy.wdater:wdater:$wdaterVersion"
                const val AutoMigrations = "app.meetacy.wdater:auto-migrations:$wdaterVersion"
            }
            object Sdk {
                const val Api = "app.meetacy.sdk:api:$meetacySdkVersion"
                const val ApiKtor = "app.meetacy.sdk:api-ktor:$meetacySdkVersion"
            }
        }
        object Postgres {
            const val Jdbc = "org.postgresql:postgresql:$postgresqlVersion"
        }
        object Logback {
            const val Classic = "ch.qos.logback:logback-classic:$logbackVersion"    
        }
        object Slf4j {
            const val Simple = "org.slf4j:slf4j-simple:$slf4jJVersion"
        }
        object Androidx {
            const val AppCompat =
                "androidx.appcompat:appcompat:$androidAppCompatVersion"
            const val Material =
                "com.google.android.material:material:$materialVersion"
            const val RecyclerView =
                "androidx.recyclerview:recyclerview:$recyclerViewVersion"
            const val SwipeRefreshLayout =
                "androidx.swiperefreshlayout:swiperefreshlayout:$swipeRefreshLayoutVersion"
            const val ConstraintLayout =
                "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
            const val Lifecycle =
                "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
            const val LifecycleKtx =
                "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"

            object Compose {
                const val UI = "androidx.compose.ui:ui:$androidComposeVersion"
                const val UITooling = "androidx.compose.ui:ui-tooling:$androidComposeVersion"
                const val Foundation = "androidx.compose.foundation:foundation:$androidComposeVersion"
                const val Material = "androidx.compose.material:material:$androidComposeVersion"
                const val Icons = "androidx.compose.material:material-icons-core:$androidComposeVersion"
                const val ExtendedIcons = "androidx.compose.material:material-icons-extended:$androidComposeVersion"
                const val JUnitTests = "androidx.compose.ui:ui-test-junit4:$androidComposeVersion"
                const val Activity = "androidx.activity:activity-compose:1.4.0-alpha02"
            }
        }
        object Bumtech {
            const val Glide =
                "com.github.bumptech.glide:glide:$glideVersion"
        }
    }
    object Kapt {
        object Bumtech {
            const val Glide = "com.github.bumptech.glide:compiler:$glideVersion"
        }
    }
    object Plugins {
        object Configuration {
            object Kotlin {
                const val Mpp = "k-mpp"
                const val Jvm = "k-jvm"
                const val Js = "k-js"

                object Android {
                    const val App = "k-android-app"
                    const val Library = "k-android-library"
                }
            }
        }
        object Deploy {
            const val Id = "deploy"
        }

        object Dependencies {
            const val Id = "dependencies"
            const val Classpath = "dependencies:dependencies:SNAPSHOT"
        }

        object Kotlin {
            const val Multiplatform = "org.jetbrains.kotlin.multiplatform"
            const val Jvm = "org.jetbrains.kotlin.jvm"
            const val Js = "org.jetbrains.kotlin.js"
            const val Android = "org.jetbrains.kotlin.android"
            const val Classpath = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
            const val Test = "org.jetbrains.kotlin:kotlin-test:$kotlinVersion"
        }
        object Android {
            const val Application = "com.android.application"
            const val Library = "com.android.library"
            const val Classpath = "com.android.tools.build:gradle:$androidGradlePluginVersion"
        }
        object Serialization {
            const val Id = "org.jetbrains.kotlin.plugin.serialization"
            const val Classpath = "org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion"
        }
        object Ssh {
            const val Id = "org.hidetake.ssh"
            const val Classpath = "org.hidetake:gradle-ssh-plugin:$sshVersion"
        }
        object Publish {
            const val Id = "publish"
            const val Classpath = "publish:publish:SNAPSHOT"
        }
        object MavenPublish {
            const val Id = "maven-publish"
        }
        object Application {
            const val Id = "application"
        }
        object Shadow {
            const val Classpath = "gradle.plugin.com.github.jengelman.gradle.plugins:shadow:$shadowVer"
            const val Id = "com.github.johnrengelman.shadow"
        }
    }
    object Projects {
        object Invitation {
            // components
            const val Database = ":invitation:database"
            const val Endpoints = ":invitation:endpoints"
            const val Usecase = ":invitation:usecase"
            const val Types = ":invitation:types"
            const val it = ":invitation"
            // integrations for each component (database, usecase, etc.)
            const val DatabaseIntegrations = ":invitation:database:integrations"
            const val UsecaseIntegrations = ":invitation:usecase:integrations"
        }

        object Notification {
            // components
            const val Database = ":notification:database"
            const val Endpoints = ":notification:endpoints"
            const val Usecase = ":notification:usecase"
            const val Types = ":notification:types"
            const val it = ":notification"
            // integrations for each component (database, usecase, etc.)
            const val DatabaseIntegrations = ":notification:database:integrations"
            const val UsecaseIntegrations = ":notification:usecase:integrations"
        }

        object Friends {
            // components
            const val Database = ":friends:database"
            const val Endpoints = ":friends:endpoints"
            const val Usecase = ":friends:usecase"
            const val Types = ":friends:types"
            const val it = ":friends"
            // integrations for each component (database, usecase, etc.)
            const val DatabaseIntegrations = ":friends:database:integrations"
            const val UsecaseIntegrations = ":friends:usecase:integrations"
        }

        object Updates {
            // components
            const val Database = ":updates:database"
            const val Endpoints = ":updates:endpoints"
            const val Usecase = ":updates:usecase"
            const val Types = ":updates:types"
            const val it = ":updates"
            // integrations for each component (database, usecase, etc.)
            const val DatabaseIntegrations = ":updates:database:integrations"
            const val UsecaseIntegrations = ":updates:usecase:integrations"
        }

        object Files {
            // components
            const val Database = ":files:database"
            const val Endpoints = ":files:endpoints"
            const val Usecase = ":files:usecase"
            const val Types = ":files:types"
            const val it = ":files"
            // integrations for each component (database, usecase, etc.)
            const val DatabaseIntegrations = ":files:database:integrations"
            const val UsecaseIntegrations = ":files:usecase:integrations"
        }

        object Meetings {
            // components
            const val Database = ":meetings:database"
            const val Endpoints = ":meetings:endpoints"
            const val Usecase = ":meetings:usecase"
            const val Types = ":meetings:types"
            const val it = ":meetings"
            // integrations for each component (database, usecase, etc.)
            const val DatabaseIntegrations = ":meetings:database:integrations"
            const val UsecaseIntegrations = ":meetings:usecase:integrations"
        }

        object User {
            // components
            const val Database = ":user:database"
            const val Endpoints = ":user:endpoints"
            const val Usecase = ":user:usecase"
            const val Types = ":user:types"
            const val it = ":user"
            // integrations for each component (database, usecase, etc.)
            const val DatabaseIntegrations = ":user:database:integrations"
            const val UsecaseIntegrations = ":user:usecase:integrations"
        }

        object Auth {
            // components
            const val Database = ":auth:database"
            const val Endpoints = ":auth:endpoints"
            const val Usecase = ":auth:usecase"
            const val Types = ":auth:types"
            const val it = ":auth"
            // integrations for each component (database, usecase, etc.)
            const val DatabaseIntegrations = ":auth:database:integrations"
            const val UsecaseIntegrations = ":auth:usecase:integrations"
        }

        object Email {
            // components
            const val Database = ":email:database"
            const val Endpoints = ":email:endpoints"
            const val Usecase = ":email:usecase"
            const val Types = ":email:types"
            const val it = ":email"
            // integrations for each component (database, usecase, etc.)
            const val DatabaseIntegrations = ":email:database:integrations"
            const val UsecaseIntegrations = ":email:usecase:integrations"
        }

        object UsecaseUtf8Checker {
            const val Usecase = ":utf8-checker:usecase"
            const val it = ":utf8-checker"
        }

        object UsecaseHashGenerator {
            const val Usecase = ":hash-generator:usecase"
            const val it = ":hash-generator"
        }

        object Paging {
            const val Types = ":paging:types"
            const val Database = ":paging:database"
            const val it = ":paging"
        }

        const val Constants = ":constants"
        const val Optional = ":optional"
        const val AmountTypes = ":amount"
        const val DateTime = ":datetime"
        const val LocationTypes = ":location"
        const val ExposedExtensions = ":exposed-extensions"
        const val Annotations = ":annotations"
        const val EndpointsNew = ":endpoints-new"
        const val Migrations = ":migrations"
        const val KtorExtensions = ":ktor-extensions"
        const val Types = ":types"
        const val TypesSerialization = ":types:serialization-integration"
        const val HashGenerator = ":libs:hash-generator"
        const val HashGeneratorUsecase = ":libs:hash-generator:usecase-integration"
        const val Utf8Checker = ":libs:utf8-checker"
        const val Utf8CheckerUsecase = ":libs:utf8-checker:usecase-integration"
        const val StdlibExtensions = ":libs:stdlib-extensions"
        const val DI = ":libs:di"
        const val DiscordWebhook = ":libs:discord-webhook"
        const val DiscordWebhookKtor = ":libs:discord-webhook:ktor"
    }
}

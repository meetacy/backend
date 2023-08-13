rootProject.name = "meetacy-backend"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven {
            url = uri("https://maven.pkg.github.com/meetacy/maven")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

includeBuild("build-logic")

include(
    "application",
    "application:endpoints",
    "application:usecase",
    "application:database",
    "application:database:migrations"
)

val core = listOf(
    "constants",
    "endpoints",
    "endpoints:integration",
    "usecase",
    "usecase:integration",
    "database",
    "integration",
    "types",
    "types:integration",
    "types:serializable",
    "types:serializable:integration",
)
include(core.map { "core:$it" })

val libraries = listOf(
    "hash-generator",
    "utf8-checker",
    "stdlib-extensions",
    "discord-webhook",
    "discord-webhook:ktor",
    "paging",
    "paging:database",
    "paging:serializable",
    "paging:serializable:integration",
    "exposed-extensions",
    "ktor-extensions",
)
include(libraries.map { "libs:$it" })

val features = listOf(
    "auth",
    "email",
    "files",
    "friends",
    "invitations",
    "meetings",
    "notifications",
    "updates",
    "users"
)

features.forEach { feature ->
    include("feature:$feature:endpoints")
    include("feature:$feature:endpoints:integration")
    include("feature:$feature:usecase")
    include("feature:$feature:usecase:integration")
    include("feature:$feature:database")
    // fixme: database integrations are against architecture
    //  remove it later (shoulda move it to usecase:integration)
    include("feature:$feature:database:integration")
}

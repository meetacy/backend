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

include(
    "core",
    "core:constants",
    "core:database",
    "core:integration",
    "core:types",
    "core:types:integration",
    "core:types-serializable",
    "core:types-serializable:integration",
)

val libraries = listOf(
    "hash-generator",
    "utf8-checker",
    "stdlib-extensions",
    "discord-webhook",
    "discord-webhook:ktor",
    "paging",
    "paging:types",
    "paging:database",
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
    "user"
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

//include(
//    "feature",
//    "feature:auth",
//    "feature:auth:database",
//    "feature:auth:database:integrations",
//    "feature:auth:endpoints",
//    "feature:auth:usecase",
//    "feature:auth:usecase:integrations",
//    "feature:email",
//    "feature:email:database",
//    "feature:email:database:integrations",
//    "feature:email:usecase",
//    "feature:email:usecase:integrations",
//    "feature:email:endpoints",
//    "feature:files",
//    "feature:files:database",
//    "feature:files:database:integrations",
//    "feature:files:usecase",
//    "feature:files:usecase:integrations",
//    "feature:files:endpoints",
//    "feature:friends",
//    "feature:friends:database",
//    "feature:friends:database:integrations",
//    "feature:friends:usecase",
//    "feature:friends:usecase:integrations",
//    "feature:friends:endpoints",
//    "feature:invitations",
//    "feature:invitations:usecase",
//    "feature:invitations:usecase:integrations",
//    "feature:invitations:endpoints",
//    "feature:invitations:database",
//    "feature:invitations:database:integrations",
//    "feature:meetings",
//    "feature:meetings:endpoints",
//    "feature:meetings:database",
//    "feature:meetings:database:integrations",
//    "feature:meetings:usecase",
//    "feature:meetings:usecase:integrations",
//    "feature:notification",
//    "feature:notification:database",
//    "feature:notification:database:integrations",
//    "feature:notification:usecase",
//    "feature:notification:usecase:integrations",
//    "feature:notification:endpoints",
//    "feature:updates",
//    "feature:updates:database",
//    "feature:updates:database:integrations",
//    "feature:updates:usecase",
//    "feature:updates:usecase:integrations",
//    "feature:updates:endpoints",
//    "feature:user",
//    "feature:user:endpoints",
//    "feature:user:database",
//    "feature:user:database:integrations",
//    "feature:user:usecase",
//    "feature:user:usecase:integrations"
//)

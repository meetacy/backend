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
    "annotations",
    "constants",
    "datetime",
    "migrations",
//    "endpoints-new",
    "feature:common:integration",
    "feature:common:types",
    "feature:common:types:integration",
    "feature:invitations:database",
    "feature:invitations:types",
    "feature:meetings:database",
    "feature:users:database",
    "libs:hash-generator"
//    "libs:utf8-checker",
//    "libs:utf8-checker:usecase-integration",
//    "libs:stdlib-extensions",
//    "libs:di",
//    "libs:discord-webhook",
//    "libs:discord-webhook:ktor"
)

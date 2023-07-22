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
//    "application",
    "annotations", // migrated
    "amount", // migrated
    "constants", // migrated
    "datetime", // migrated
    "migrations", // TODO: migrate
//    "endpoints-new",
    "libs:hash-generator", // migrated
//    "libs:utf8-checker",
//    "libs:utf8-checker:usecase-integration",
    "libs:stdlib-extensions", // migrated
    "libs:discord-webhook", // migrated
    "libs:discord-webhook:ktor", // migrated
    "libs:paging", // migrated
    "libs:paging:types", // migrated
    "libs:paging:database", // migrated
    "libs:exposed-extensions", // migrated
)

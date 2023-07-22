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
    "feature", // migrated
    "feature:common", // migrated
    "feature:common:integration", // migrated
    "feature:common:types", // migrated
    "feature:common:types:integration", // migrated
    "feature:auth", // migrated
    "feature:auth:database", // migrated
    "feature:auth:database:integrations", // migrated
    "feature:auth:endpoints", // migrated
    "feature:auth:usecase", // migrated
    "feature:auth:usecase:integrations", // migrated
    "feature:auth:types", // migrated
    "feature:files:types", // migrated
    "feature:files:usecase", // migrated
    "feature:user:database", // migrated
    "feature:user:usecase", // TODO: migrate
    "feature:user:types", // migrated
    "location", // migrated
    "migrations", // TODO: migrate
//    "endpoints-new",
    "optional", // migrated
    "utf8-checker", // migrated
    "utf8-checker:usecase", // migrated
    "libs:hash-generator", // migrated
    "libs:utf8-checker", // migrated
    "libs:utf8-checker:usecase-integration", // migrated
    "libs:stdlib-extensions", // migrated
    "libs:discord-webhook", // migrated
    "libs:discord-webhook:ktor", // migrated
    "libs:paging", // migrated
    "libs:paging:types", // migrated
    "libs:paging:database", // migrated
    "libs:exposed-extensions", // migrated
    "libs:ktor-extensions", // migrated
)

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
    "application", // TODO: migrate
    "annotations", // migrated
    "amount", // migrated
    "constants", // migrated
    "datetime", // migrated
    "feature", // migrated
    "feature:auth", // migrated
    "feature:auth:database", // migrated
    "feature:auth:database:integrations", // migrated
    "feature:auth:endpoints", // migrated
    "feature:auth:usecase", // migrated
    "feature:auth:usecase:integrations", // migrated
    "feature:auth:types", // migrated
    "feature:common", // migrated
    "feature:common:integration", // migrated
    "feature:common:types", // migrated
    "feature:common:types:integration", // migrated
    "feature:email", // TODO: migrate
    "feature:email:database", // TODO: migrate
    "feature:email:database:integrations", // TODO: migrate
    "feature:email:usecase", // TODO: migrate
    "feature:email:usecase:integrations", // TODO: migrate
    "feature:email:endpoints", // migrated
    "feature:email:types", // migrated
    "feature:files", // migrated
    "feature:files:types", // migrated
    "feature:files:database", // migrated
    "feature:files:database:integrations", // migrated
    "feature:files:usecase", // migrated
    "feature:files:usecase:integrations", // migrated
    "feature:files:endpoints", // migrated
    "feature:friends", // migrated
    "feature:friends:database", // migrated
    "feature:friends:database:integrations", // migrated
    "feature:friends:usecase", // migrated
    "feature:friends:usecase:integrations", // migrated
    "feature:friends:endpoints", // migrated
    "feature:invitations", // migrated
    "feature:invitations:types", // migrated
    "feature:invitations:usecase", // migrated
    "feature:invitations:usecase:integrations", // migrated
    "feature:invitations:endpoints", // migrated
    "feature:invitations:database", // migrated
    "feature:invitations:database:integrations", // migrated
    "feature:meetings", // migrated
    "feature:meetings:endpoints", // migrated
    "feature:meetings:database", // migrated
    "feature:meetings:database:integrations", // migrated
    "feature:meetings:usecase", // migrated
    "feature:meetings:usecase:integrations", // migrated
    "feature:meetings:types", // migrated
    "feature:notification", // migrated
    "feature:notification:database", // migrated
    "feature:notification:database:integrations", // migrated
    "feature:notification:usecase", // migrated
    "feature:notification:usecase:integrations", // migrated
    "feature:notification:endpoints", // migrated
    "feature:notification:types", // migrated
    "feature:updates", // migrated
    "feature:updates:database", // migrated
    "feature:updates:database:integrations", // migrated
    "feature:updates:usecase", // migrated
    "feature:updates:usecase:integrations", // migrated
    "feature:updates:endpoints", // migrated
    "feature:updates:types", // migrated
    "feature:user", // migrated
    "feature:user:database", // migrated
    "feature:user:database:integrations", // migrated
    "feature:user:usecase", // migrated
    "feature:user:usecase:integrations", // migrated
    "feature:user:endpoints", // migrated
    "feature:user:types", // migrated
    "location", // migrated
    "migrations", // migrated
    "endpoints-new", // migrated
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

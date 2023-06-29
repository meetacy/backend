pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}

rootProject.name = "Meetacy Backend Application"

includeBuild("buildUtils/dependencies")
includeBuild("buildUtils/configuration")
includeBuild("buildUtils/service-deploy")

include(
    "application",
    "endpoints",
    "usecase",
    "usecase:endpoints-integration",
    "database",
    "types",
    "types:serialization-integration",
    "libs:hash-generator",
    "libs:hash-generator:usecase-integration",
    "libs:utf8-checker",
    "libs:utf8-checker:usecase-integration",
    "libs:stdlib-extensions"
)
include("database:invitations")
findProject(":database:invitations")?.name = "invitations"
include("database:invitations:integrations")
findProject(":database:invitations:integrations")?.name = "integrations"
include("database:users")
findProject(":database:users")?.name = "users"
include("database:types")
findProject(":database:types")?.name = "types"
include("database:migrations")
findProject(":database:migrations")?.name = "migrations"
include("database:types:integrations")
findProject(":database:types:integrations")?.name = "integrations"
include("database:users")
findProject(":database:users")?.name = "users"
include("database:users:integrations")
findProject(":database:users:integrations")?.name = "integrations"
include("database:meetings")
findProject(":database:meetings")?.name = "meetings"
include("database:meetings:integrations")
findProject(":database:meetings:integrations")?.name = "integrations"
include("database:files")
findProject(":database:files")?.name = "files"
include("database:transactions")
findProject(":database:transactions")?.name = "transactions"
include("database:files:integrations")
findProject(":database:files:integrations")?.name = "integrations"
include("database:friends")
findProject(":database:friends")?.name = "friends"
include("database:friends:integrations")
findProject(":database:friends:integrations")?.name = "integrations"
include("database:auth")
findProject(":database:auth")?.name = "auth"
include("database:auth:integrations")
findProject(":database:auth:integrations")?.name = "integrations"
include("database:email")
findProject(":database:email")?.name = "email"
include("database:email:integrations")
findProject(":database:email:integrations")?.name = "integrations"
include("database:notifications")
findProject(":database:notifications")?.name = "notifications"
include("database:notifications:integrations")
findProject(":database:notifications:integrations")?.name = "integrations"
include("database:location")
findProject(":database:location")?.name = "location"
include("database:location:integrations")
findProject(":database:location:integrations")?.name = "integrations"
include("usecase:auth")
findProject(":usecase:auth")?.name = "auth"
include("usecase:auth:integrations")
findProject(":usecase:auth:integrations")?.name = "integrations"
include("usecase:invitations")
findProject(":usecase:invitations")?.name = "invitations"
include("usecase:invitations:integrations")
findProject(":usecase:invitations:integrations")?.name = "integrations"
include("usecase:friends")
findProject(":usecase:friends")?.name = "friends"
include("usecase:friends:integrations")
findProject(":usecase:friends:integrations")?.name = "integrations"
include("usecase:email")
findProject(":usecase:email")?.name = "email"
include("usecase:email:integrations")
findProject(":usecase:email:integrations")?.name = "integrations"
include("usecase:location")
findProject(":usecase:location")?.name = "location"
include("usecase:files")
findProject(":usecase:files")?.name = "files"
include("usecase:files:integrations")
findProject(":usecase:files:integrations")?.name = "integrations"

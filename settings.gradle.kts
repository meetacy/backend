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
    "database:usecase-integration",
    "database:endpoints-integration",
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
include("database:init")
findProject(":database:init")?.name = "init"
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

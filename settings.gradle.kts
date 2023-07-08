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
    "libs:stdlib-extensions",
    "libs:di"
)
include("invitation")
include("invitation:database")
findProject(":invitation:database")?.name = "database"
include("invitation:usecase")
findProject(":invitation:usecase")?.name = "usecase"
include("invitation:endpoints")
findProject(":invitation:endpoints")?.name = "endpoints"
include("invitation:types")
findProject(":invitation:types")?.name = "types"
include("invitation:usecase:integrations")
findProject(":invitation:usecase:integrations")?.name = "integrations"
include("invitation:database:integrations")
findProject(":invitation:database:integrations")?.name = "integrations"
include("invitation:types:integrations")
findProject(":invitation:types:integrations")?.name = "integrations"
include("ktor-extensions")
include("database-extensions")
include("endpoints-new")
include("notification")
include("notification:database")
findProject(":notification:database")?.name = "database"
include("notification:usecase")
findProject(":notification:usecase")?.name = "usecase"
include("notification:endpoints")
findProject(":notification:endpoints")?.name = "endpoints"
include("notification:database:integrations")
findProject(":notification:database:integrations")?.name = "integrations"
include("notification:usecase:integrations")
findProject(":notification:usecase:integrations")?.name = "integrations"
include("notification:types")
findProject(":notification:types")?.name = "types"
include("friends")
include("friends:database")
findProject(":friends:database")?.name = "database"
include("friends:usecase")
findProject(":friends:usecase")?.name = "usecase"
include("friends:endpoints")
findProject(":friends:endpoints")?.name = "endpoints"
include("friends:types")
findProject(":friends:types")?.name = "types"
include("friends:database:integrations")
findProject(":friends:database:integrations")?.name = "integrations"
include("friends:usecase:integrations")
findProject(":friends:usecase:integrations")?.name = "integrations"
include("updates")
include("updates:database")
findProject(":updates:database")?.name = "database"
include("updates:usecase")
findProject(":updates:usecase")?.name = "usecase"
include("updates:endpoints")
findProject(":updates:endpoints")?.name = "endpoints"
include("updates:types")
findProject(":updates:types")?.name = "types"
include("updates:database:integrations")
findProject(":updates:database:integrations")?.name = "integrations"
include("updates:usecase:integrations")
findProject(":updates:usecase:integrations")?.name = "integrations"

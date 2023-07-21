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
    "libs:hash-generator",
    "libs:hash-generator:usecase-integration",
    "libs:utf8-checker",
    "libs:utf8-checker:usecase-integration",
    "libs:stdlib-extensions",
    "libs:di",
    "libs:discord-webhook",
    "libs:discord-webhook:ktor"
)
include("feature:invitation")
include("feature:invitation:database")
findProject(":feature:invitation:database")?.name = "database"
include("feature:invitation:usecase")
findProject(":feature:invitation:usecase")?.name = "usecase"
include("feature:invitation:endpoints")
findProject(":feature:invitation:endpoints")?.name = "endpoints"
include("feature:invitation:types")
findProject(":feature:invitation:types")?.name = "types"
include("feature:invitation:usecase:integrations")
findProject(":feature:invitation:usecase:integrations")?.name = "integrations"
include("feature:invitation:database:integrations")
findProject(":feature:invitation:database:integrations")?.name = "integrations"
include("feature:invitation:types:integrations")
findProject(":feature:invitation:types:integrations")?.name = "integrations"
include("ktor-extensions")
include("migrations")
include("endpoints-new")
include("feature:notification")
include("feature:notification:database")
findProject(":feature:notification:database")?.name = "database"
include("feature:notification:usecase")
findProject(":feature:notification:usecase")?.name = "usecase"
include("feature:notification:endpoints")
findProject(":feature:notification:endpoints")?.name = "endpoints"
include("feature:notification:database:integrations")
findProject(":feature:notification:database:integrations")?.name = "integrations"
include("feature:notification:usecase:integrations")
findProject(":feature:notification:usecase:integrations")?.name = "integrations"
include("feature:notification:types")
findProject(":feature:notification:types")?.name = "types"
include("feature:friends")
include("feature:friends:database")
findProject(":feature:friends:database")?.name = "database"
include("feature:friends:usecase")
findProject(":feature:friends:usecase")?.name = "usecase"
include("feature:friends:endpoints")
findProject(":feature:friends:endpoints")?.name = "endpoints"
include("feature:friends:types")
findProject(":feature:friends:types")?.name = "types"
include("feature:friends:database:integrations")
findProject(":feature:friends:database:integrations")?.name = "integrations"
include("feature:friends:usecase:integrations")
findProject(":feature:friends:usecase:integrations")?.name = "integrations"
include("feature:updates")
include("feature:updates:database")
findProject(":feature:updates:database")?.name = "database"
include("feature:updates:usecase")
findProject(":feature:updates:usecase")?.name = "usecase"
include("feature:updates:endpoints")
findProject(":feature:updates:endpoints")?.name = "endpoints"
include("feature:updates:types")
findProject(":feature:updates:types")?.name = "types"
include("feature:updates:database:integrations")
findProject(":feature:updates:database:integrations")?.name = "integrations"
include("feature:updates:usecase:integrations")
findProject(":feature:updates:usecase:integrations")?.name = "integrations"
include("feature:files")
include("feature:files:database")
findProject(":feature:files:database")?.name = "database"
include("feature:files:usecase")
findProject(":feature:files:usecase")?.name = "usecase"
include("feature:files:endpoints")
findProject(":feature:files:endpoints")?.name = "endpoints"
include("feature:files:database:integrations")
findProject(":feature:files:database:integrations")?.name = "integrations"
include("feature:files:types")
findProject(":feature:files:types")?.name = "types"
include("feature:files:usecase:integrations")
findProject(":feature:files:usecase:integrations")?.name = "integrations"
include("feature:meetings")
include("feature:meetings:endpoints")
findProject(":feature:meetings:endpoints")?.name = "endpoints"
include("feature:meetings:types")
findProject(":feature:meetings:types")?.name = "types"
include("feature:meetings:usecase")
findProject(":feature:meetings:usecase")?.name = "usecase"
include("feature:meetings:database")
findProject(":feature:meetings:database")?.name = "database"
include("feature:meetings:database:integrations")
findProject(":feature:meetings:database:integrations")?.name = "integrations"
include("feature:meetings:usecase:integrations")
findProject(":feature:meetings:usecase:integrations")?.name = "integrations"
include("feature:user")
include("feature:user:database")
findProject(":feature:user:database")?.name = "database"
include("feature:user:database:integrations")
findProject(":feature:user:database:integrations")?.name = "integrations"
include("feature:user:usecase")
findProject(":feature:user:usecase")?.name = "usecase"
include("feature:user:endpoints")
findProject(":feature:user:endpoints")?.name = "endpoints"
include("feature:user:types")
findProject(":feature:user:types")?.name = "types"
include("feature:user:usecase:integrations")
findProject(":feature:user:usecase:integrations")?.name = "integrations"
include("feature:auth")
include("feature:auth:database")
findProject(":feature:auth:database")?.name = "database"
include("feature:auth:endpoints")
findProject(":feature:auth:endpoints")?.name = "endpoints"
include("feature:auth:types")
findProject(":feature:auth:types")?.name = "types"
include("feature:auth:usecase")
findProject(":feature:auth:usecase")?.name = "usecase"
include("feature:auth:database:integrations")
findProject(":feature:auth:database:integrations")?.name = "integrations"
include("feature:auth:usecase:integrations")
findProject(":feature:auth:usecase:integrations")?.name = "integrations"
include("feature:email")
include("feature:email:database")
findProject(":feature:email:database")?.name = "database"
include("feature:email:usecase")
findProject(":feature:email:usecase")?.name = "usecase"
include("feature:email:endpoints")
findProject(":feature:email:endpoints")?.name = "endpoints"
include("feature:email:types")
findProject(":feature:email:types")?.name = "types"
include("feature:email:database:integrations")
findProject(":feature:email:database:integrations")?.name = "integrations"
include("feature:email:usecase:integrations")
findProject(":feature:email:usecase:integrations")?.name = "integrations"
include("utf8-checker")
include("utf8-checker:usecase")
findProject(":utf8-checker:usecase")?.name = "usecase"
include("hash-generator")
include("hash-generator:usecase")
findProject(":hash-generator:usecase")?.name = "usecase"
include("paging")
include("paging:types")
findProject(":paging:types")?.name = "types"
include("paging:database")
findProject(":paging:database")?.name = "database"
include("annotations")
include("exposed-extensions")
include("location")
include("datetime")
include("amount")
include("optional")
include("constants")
include("feature")

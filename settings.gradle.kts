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
    "types",
    "types:serialization-integration",
    "libs:hash-generator",
    "libs:hash-generator:usecase-integration",
    "libs:utf8-checker",
    "libs:utf8-checker:usecase-integration",
    "libs:stdlib-extensions",
    "libs:di",
    "libs:discord-webhook",
    "libs:discord-webhook:ktor"
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
include("migrations")
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
include("files")
include("files:database")
findProject(":files:database")?.name = "database"
include("files:usecase")
findProject(":files:usecase")?.name = "usecase"
include("files:endpoints")
findProject(":files:endpoints")?.name = "endpoints"
include("files:database:integrations")
findProject(":files:database:integrations")?.name = "integrations"
include("files:types")
findProject(":files:types")?.name = "types"
include("files:usecase:integrations")
findProject(":files:usecase:integrations")?.name = "integrations"
include("meetings")
include("meetings:endpoints")
findProject(":meetings:endpoints")?.name = "endpoints"
include("meetings:types")
findProject(":meetings:types")?.name = "types"
include("meetings:usecase")
findProject(":meetings:usecase")?.name = "usecase"
include("meetings:database")
findProject(":meetings:database")?.name = "database"
include("meetings:database:integrations")
findProject(":meetings:database:integrations")?.name = "integrations"
include("meetings:usecase:integrations")
findProject(":meetings:usecase:integrations")?.name = "integrations"
include("user")
include("user:database")
findProject(":user:database")?.name = "database"
include("user:database:integrations")
findProject(":user:database:integrations")?.name = "integrations"
include("user:usecase")
findProject(":user:usecase")?.name = "usecase"
include("user:endpoints")
findProject(":user:endpoints")?.name = "endpoints"
include("user:types")
findProject(":user:types")?.name = "types"
include("user:usecase:integrations")
findProject(":user:usecase:integrations")?.name = "integrations"
include("auth")
include("auth")
include("auth:database")
findProject(":auth:database")?.name = "database"
include("auth:endpoints")
findProject(":auth:endpoints")?.name = "endpoints"
include("auth:types")
findProject(":auth:types")?.name = "types"
include("auth:usecase")
findProject(":auth:usecase")?.name = "usecase"
include("auth:database:integrations")
findProject(":auth:database:integrations")?.name = "integrations"
include("auth:usecase:integrations")
findProject(":auth:usecase:integrations")?.name = "integrations"
include("email")
include("email:database")
findProject(":email:database")?.name = "database"
include("email:usecase")
findProject(":email:usecase")?.name = "usecase"
include("email:endpoints")
findProject(":email:endpoints")?.name = "endpoints"
include("email:types")
findProject(":email:types")?.name = "types"
include("email:database:integrations")
findProject(":email:database:integrations")?.name = "integrations"
include("email:usecase:integrations")
findProject(":email:usecase:integrations")?.name = "integrations"
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
include("date-time")
include("amount")

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
//includeBuild("buildUtils/library-deploy")

include(
    "application",
    "endpoints",
    "usecase",
    "usecase:endpoints-integration",
    "types",
    "types:serialization-integration",
    "hash-generator",
    "hash-generator:usecase-integration",
    "database",
    "database:usecase-integration",
    "database:endpoints-integration",
    "utf8-checker",
    "utf8-checker:usecase-integration"
)

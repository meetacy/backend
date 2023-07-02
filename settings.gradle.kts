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

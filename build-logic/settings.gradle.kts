dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

val scripts = listOf(
    "feature-gen"
)
include(scripts.map { "scripts:$it" })

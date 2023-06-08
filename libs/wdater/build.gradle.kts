plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(Deps.Libs.Exposed.Core)
}

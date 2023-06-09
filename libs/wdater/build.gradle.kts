plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

kotlin {
    explicitApi()
    sourceSets.all {
        languageSettings {
            enableLanguageFeature("ContextReceivers")
        }
    }
}

dependencies {
    implementation(Deps.Libs.Exposed.Core)
}

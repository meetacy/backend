plugins {
    `kotlin-dsl`
    id(Deps.Plugins.Dependencies.Id)
}

group = "service-deploy"
version = "SNAPSHOT"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

gradlePlugin {
    plugins.register("deploy") {
        id = "deploy"
        implementationClass = "DeployPlugin"
    }
}

dependencies {
    implementation(Deps.Plugins.Dependencies.Classpath)
    implementation(Deps.Plugins.Ssh.Classpath)
    implementation(Deps.Plugins.Shadow.Classpath)
}

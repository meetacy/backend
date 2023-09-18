package app.meetacy.script.featureGen

import app.meetacy.script.featureGen.stdlibExtensions.capitalize
import java.io.File
import kotlin.system.exitProcess

internal val userDir = File("").run { File(absolutePath) }

internal val File.features get() = File(this, "feature")
internal val File.settingsGradleKts get() = File(this, "settings.gradle.kts")

internal val File.endpoints get() = File(this, "endpoints")
internal val File.routingKt get() = File(this, "Routing.kt")
internal val File.usecase get() = File(this, "usecase")
internal val File.database get() = File(this, "database")
internal fun File.storageKt(featureName: FeatureName) = File(this, "${featureName.camelCase.capitalize()}Storage.kt")
internal val File.buildGradleKts get() = File(this, "build.gradle.kts")

internal val File.kotlinMain get() = File(this, "src/main/kotlin")

internal val File.featurePackage get() = packageDir("app.meetacy.backend.feature")

internal fun File.packageDir(pkg: String) = File(this, pkg.replace('.', '/'))

internal val File.integration get() = File(this, "integration")

internal val File.diKt get() = File(this, "DI.kt")

internal fun checkFiles(userDir: File) {
    if (!userDir.features.exists()) {
        failScript("Current working directory: ${userDir.absolutePath}\n`feature` directory does not exist, try to run program in a `project` root")
    }
    if (!userDir.settingsGradleKts.exists()) {
        println("Current working directory: ${userDir.absolutePath}\nCannot find `settings.gradle.kts`, try to run program in a `project` root")
        exitProcess(1)
    }
}

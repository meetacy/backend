package app.meetacy.script.featureGen

import java.io.File
import app.meetacy.script.featureGen.userDir as userDirectory

fun initFeature(
    featureName: FeatureName,
    userDir: File = userDirectory,
) {
    checkFiles(userDir)
    val featureDir = initFeatureDirectory(featureName, userDir)
    initEndpoints(featureName, featureDir)
    initUsecase(featureName, featureDir)
    initDatabase(featureName, featureDir)
    appendFeatureToSettingsGradle(userDir, featureName)
}

private fun initFeatureDirectory(
    name: FeatureName,
    userDir: File
): File {
    val featureDir = File(userDir.features, name.dashCase)

    if (featureDir.exists()) {
        failScript("Directory 'feature/${name.dashCase}' is not empty. Cannot create feature $name")
    }

    featureDir.mkdir()

    return featureDir
}

private fun initEndpoints(
    featureName: FeatureName,
    featureDir: File
) {
    val endpointsDir = featureDir.endpoints.apply { mkdir() }
    val buildscript = endpointsDir.buildGradleKts.apply { createNewFile() }
    buildscript.writeText(endpointsBuildGradleKts())

    val sourcesDir = endpointsDir
        .kotlinMain
        .featurePackage
        .packageDir("${featureName.camelCase}.endpoints")

    sourcesDir.mkdirs()

    initEndpointsIntegration(featureName, endpointsDir)
}

private fun initEndpointsIntegration(
    featureName: FeatureName,
    endpointsDir: File
) {
    val integrationDir = endpointsDir.integration.apply { mkdir() }

    integrationDir.buildGradleKts.writeText(
        endpointsIntegrationBuildGradleKts(featureName)
    )

    val sourcesDir = integrationDir
        .kotlinMain
        .featurePackage
        .packageDir("${featureName.camelCase}.endpoints.integration")
        .apply { mkdirs() }

    sourcesDir.routingKt.writeText(endpointsIntegrationRoutingFile(featureName))
}

private fun initUsecase(
    featureName: FeatureName,
    featureDir: File
) {
    val usecaseDir = featureDir.usecase.apply { mkdir() }
    val buildscript = usecaseDir.buildGradleKts.apply { createNewFile() }
    buildscript.writeText(usecaseBuildGradleKts())

    val sourcesDir = usecaseDir
        .kotlinMain
        .featurePackage
        .packageDir("${featureName.camelCase}.usecase")

    sourcesDir.mkdirs()

    initUsecaseIntegration(featureName, usecaseDir)
}

private fun initUsecaseIntegration(
    featureName: FeatureName,
    usecaseDir: File
) {
    val integrationDir = usecaseDir.integration.apply { mkdir() }

    integrationDir.buildGradleKts.writeText(
        usecaseIntegrationBuildGradleKts(featureName)
    )

    val sourcesDir = integrationDir
        .kotlinMain
        .featurePackage
        .packageDir("${featureName.camelCase}.usecase.integration")

    sourcesDir.mkdirs()

    sourcesDir.diKt.writeText(
        diIntegrationFile(module = "usecase", featureName)
    )
}

private fun initDatabase(
    featureName: FeatureName,
    featureDir: File
) {
    val databaseDir = featureDir.database.apply { mkdirs() }
    val buildscript = databaseDir.buildGradleKts.apply { createNewFile() }
    buildscript.writeText(databaseBuildGradleKts())

    val sourcesDir = databaseDir
        .kotlinMain
        .featurePackage
        .packageDir("${featureName.camelCase}.database")
        .apply { mkdirs() }

    val storageKt = sourcesDir.storageKt(featureName)

    storageKt.writeText(databaseStorageFile(featureName))

    initDatabaseIntegration(featureName, databaseDir)
}

private fun initDatabaseIntegration(
    featureName: FeatureName,
    databaseDir: File
) {
    val integrationDir = databaseDir.integration.apply { mkdir() }

    integrationDir.buildGradleKts.writeText(
        databaseIntegrationBuildGradleKts(featureName)
    )

    val sourcesDir = integrationDir
        .kotlinMain
        .featurePackage
        .packageDir("${featureName.camelCase}.database.integration")

    sourcesDir.mkdirs()

    sourcesDir.diKt.writeText(
        diIntegrationFile(module = "database", featureName)
    )
}

private fun appendFeatureToSettingsGradle(
    userDir: File,
    featureName: FeatureName
) {
    val text = userDir.settingsGradleKts.readText()

    val featureRegex = Regex("""(val features = listOf\(.*?)(?=\n\))""", RegexOption.DOT_MATCHES_ALL)

    val newText = text.replace(featureRegex) { result ->
        val comma = if (result.value.last() == ',') "" else ","

        result.value + comma + "\n    \"${featureName.dashCase}\""
    }

    userDir.settingsGradleKts.writeText(newText)
}

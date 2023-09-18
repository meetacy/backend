import app.meetacy.script.featureGen.FeatureName
import app.meetacy.script.featureGen.initFeature

val createFeature: Task by tasks.creating {
    group = "templates"

    doLast {
        val featureNameString = properties["featureName"]?.toString()
            ?: error("Provide `featureName` parameter for the task. You can do it for command line like this: ./gradlew -PfeatureName=test-feature")

        val featureName = FeatureName.parseOrNull(featureNameString)
            ?: error("""
                This feature has invalid name.
                Consider to use [a-z] (no uppercase symbols) and **single** dashes (-) as dividers
            """.trimIndent())

        initFeature(featureName, userDir = rootProject.projectDir)
    }
}

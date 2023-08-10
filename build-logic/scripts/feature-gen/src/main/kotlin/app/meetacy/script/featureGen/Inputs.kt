package app.meetacy.script.featureGen

internal fun checkArgs(args: Array<String>) {
    if (args.size != 1) {
        failScript("""
            This program requires 1 positional argument.
            Please call `feature-gen feature-name` to generate feature with name `feature-name`.
        """.trimIndent())
    }
}

internal inline fun parseFeatureName(
    name: String
): FeatureName {
    val featureName = FeatureName.parseOrNull(name)

    if (featureName == null) {
        failScript("""
            This feature has invalid name.
            Consider to use [a-z] (no uppercase symbols) and **single** dashes (-) as dividers
        """.trimIndent())
    }

    return featureName
}

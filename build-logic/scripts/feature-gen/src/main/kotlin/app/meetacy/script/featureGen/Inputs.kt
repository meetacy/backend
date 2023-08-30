package app.meetacy.script.featureGen

internal fun checkArgs(args: Array<String>) {
    if (args.size != 1) {
        failScript("""
            This program requires 1 positional argument.
            Please call `feature-gen feature-name` to generate feature with name `feature-name`.
        """.trimIndent())
    }
}

internal fun parseFeatureName(
    name: String
): FeatureName = FeatureName.parse(name)

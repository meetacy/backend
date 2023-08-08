package app.meetacy.script.featureGen

import app.meetacy.script.featureGen.stdlibExtensions.dashCaseToCamelCase

@JvmInline
value class FeatureName private constructor(val dashCase: String) {

    val camelCase: String get() = dashCase.dashCaseToCamelCase()

    companion object {
        val Regex: Regex = Regex("[a-z]+(-[a-z]+)*")

        fun parse(dashCase: String): FeatureName {
            return parseOrNull(dashCase) ?: error(
                """
                    This feature has invalid name.
                    Consider to use [a-z] (no uppercase symbols) and **single** dashes (-) as dividers
                """.trimIndent()
            )
        }

        fun parseOrNull(dashCase: String): FeatureName? {
            if (!dashCase.matches(Regex)) return null
            return FeatureName(dashCase)
        }
    }
}

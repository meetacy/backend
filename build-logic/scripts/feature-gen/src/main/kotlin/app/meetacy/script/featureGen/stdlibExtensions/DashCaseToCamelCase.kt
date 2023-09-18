package app.meetacy.script.featureGen.stdlibExtensions

private val dashRegex = Regex("-\\p{L}")

fun String.dashCaseToCamelCase(): String {
    return replace(dashRegex) { result -> result.value.last().titlecase() }
}

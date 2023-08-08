package app.meetacy.backend.stdlib.string

private val dashRegex = Regex("-\\p{L}")

fun String.dashCaseToCamelCase(): String {
    return replace(dashRegex) { result -> result.value.last().titlecase() }
}

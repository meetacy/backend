package app.meetacy.backend.discord

fun Throwable.prettyStacktrace() = stackTraceToString()
    .lines()
    .filter { line ->
        val trim = line.trim()
        if (!trim.startsWith("at")) return@filter true
        trim.startsWith("at app.meetacy.backend")
    }.joinToString(separator = "\n")

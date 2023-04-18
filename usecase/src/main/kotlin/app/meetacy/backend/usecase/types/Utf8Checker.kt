package app.meetacy.backend.usecase.types

interface Utf8Checker{
    fun checkString(string: String): Boolean
}

inline fun Utf8Checker.checkString(
    string: String,
    fallback: () -> Nothing
): String = if (!checkString(string)) fallback() else string

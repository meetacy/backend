package app.meetacy.backend.types.utf8Checker

interface Utf8Checker {
    fun checkString(string: String): Boolean
}

inline fun Utf8Checker.checkString(
    string: String,
    fallback: () -> Nothing
): Boolean = if (!checkString(string)) fallback() else true

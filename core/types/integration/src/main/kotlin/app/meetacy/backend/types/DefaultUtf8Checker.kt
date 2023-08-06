package app.meetacy.backend.types

import app.meetacy.backend.types.utf8Checker.Utf8Checker
import app.meetacy.backend.utf8.Utf8Checker as LibUtf8Checker

object DefaultUtf8Checker: Utf8Checker {
    override fun checkString(string: String): Boolean =
        LibUtf8Checker.checkString(string)
}

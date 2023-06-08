package app.meetacy.backend.utf8.integration

import app.meetacy.backend.utf8.Utf8Checker
import app.meetacy.backend.usecase.types.Utf8Checker as UsecaseUtf8Checker

object DefaultUtf8Checker : UsecaseUtf8Checker {
    override fun checkString(string: String): Boolean = Utf8Checker.checkString(string)
}

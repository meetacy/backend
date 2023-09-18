package app.meetacy.backend.types.integration.utf8Checker

import app.meetacy.backend.types.utf8Checker.Utf8Checker
import app.meetacy.di.builder.DIBuilder
import app.meetacy.backend.utf8.Utf8Checker as LibUtf8Checker

internal fun DIBuilder.utf8Checker() {
    val utf8Checker by singleton<Utf8Checker> {
        object : Utf8Checker {
            override fun checkString(string: String): Boolean =
                LibUtf8Checker.checkString(string)
        }
    }
}

package app.meetacy.backend.utf8

import java.io.UnsupportedEncodingException

private fun check(@Suppress("SameParameterValue") string: String): Boolean {
    return try {
        string.toByteArray(charset("UTF-8")).forEach {
            if (it.toInt() == 0) return@check false
        }
        true
    } catch (e: UnsupportedEncodingException) {
        false
    }
}

object Utf8Checker {
    fun checkString(string: String): Boolean {
        return check(string)
    }
}

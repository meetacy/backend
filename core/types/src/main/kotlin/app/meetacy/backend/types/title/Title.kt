package app.meetacy.backend.types.title

import app.meetacy.backend.constants.MEETING_TITLE_MAX_LIMIT

@JvmInline
value class Title(val string: String) {
    companion object {
        fun parse(string: String, lengthLimit: Int = MEETING_TITLE_MAX_LIMIT): Title {
            require(string.length <= lengthLimit) {
                "Title length should not exceed $MEETING_TITLE_MAX_LIMIT characters"
            }
            return Title(string)
        }

        fun parseOrNull(string: String, lengthLimit: Int = MEETING_TITLE_MAX_LIMIT): Title? {
            if (string.length > lengthLimit) return null
            return Title(string)
        }
    }
}

val String.title: Title get() = Title.parse(string = this)


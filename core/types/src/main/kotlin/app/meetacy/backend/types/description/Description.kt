package app.meetacy.backend.types.description

import app.meetacy.backend.constants.MEETING_DESCRIPTION_MAX_LIMIT

@JvmInline
value class Description(val string: String) {
    companion object {
        fun parse(string: String, lengthLimit: Int = MEETING_DESCRIPTION_MAX_LIMIT): Description {
            require(string.length <= lengthLimit) {
                "Description length should not exceed $MEETING_DESCRIPTION_MAX_LIMIT characters"
            }
            return Description(string)
        }

        fun parseOrNull(string: String, lengthLimit: Int = MEETING_DESCRIPTION_MAX_LIMIT): Description? {
            if (string.length > lengthLimit) return null
            return Description(string)
        }
    }
}

val String.description: Description get() = Description.parse(string = this)

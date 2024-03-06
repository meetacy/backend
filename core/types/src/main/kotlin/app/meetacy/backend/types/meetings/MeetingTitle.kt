package app.meetacy.backend.types.meetings

import app.meetacy.backend.constants.MEETING_TITLE_MAX_LIMIT

@JvmInline
value class MeetingTitle(val string: String) {
    companion object {
        fun parse(string: String): MeetingTitle {
            require(string.length <= MEETING_TITLE_MAX_LIMIT) {
                "Title length should not exceed $MEETING_TITLE_MAX_LIMIT characters"
            }
            return MeetingTitle(string)
        }

        fun parseOrNull(string: String, lengthLimit: Int = MEETING_TITLE_MAX_LIMIT): MeetingTitle? {
            if (string.length > lengthLimit) return null
            return MeetingTitle(string)
        }
    }
}

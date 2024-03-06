package app.meetacy.backend.types.meetings

import app.meetacy.backend.constants.MEETING_DESCRIPTION_MAX_LIMIT

@JvmInline
value class MeetingDescription(val string: String) {
    companion object {
        fun parse(string: String): MeetingDescription {
            require(string.length <= MEETING_DESCRIPTION_MAX_LIMIT) {
                "Description length should not exceed $MEETING_DESCRIPTION_MAX_LIMIT characters"
            }
            return MeetingDescription(string)
        }

        fun parseOrNull(string: String): MeetingDescription? {
            if (string.length > MEETING_DESCRIPTION_MAX_LIMIT) return null
            return MeetingDescription(string)
        }
    }
}

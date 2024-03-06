package app.meetacy.backend.types.serializable.meetings

import app.meetacy.backend.types.meetings.MeetingTitle
import app.meetacy.backend.types.serializable.serialization
import app.meetacy.backend.types.serializable.meetings.MeetingTitle as MeetingTitleSerializable

fun MeetingTitleSerializable.type() = serialization { MeetingTitle.parse(string) }
fun MeetingTitle.serializable() = MeetingTitleSerializable(string)

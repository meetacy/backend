package app.meetacy.backend.types.serializable.meetings

import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.serializable.meetings.MeetingId as MeetingIdSerializable

fun MeetingIdSerializable.type() = MeetingIdSerializable(long)
fun MeetingId.serializable() = MeetingIdSerializable(long)

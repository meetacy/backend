package app.meetacy.backend.types.serializable.meetings

import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.serializable.serialization
import app.meetacy.backend.types.serializable.meetings.MeetingId as MeetingIdSerializable

fun MeetingIdSerializable.type() = serialization { MeetingId(long) }
fun MeetingId.serializable() = MeetingIdSerializable(long)

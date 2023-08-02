package app.meetacy.backend.types.serializable.meeting

import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.serializable.meeting.MeetingId as MeetingIdSerializable


fun MeetingIdSerializable.type() = MeetingIdSerializable(long)

fun MeetingId.serializable() = MeetingIdSerializable(long)

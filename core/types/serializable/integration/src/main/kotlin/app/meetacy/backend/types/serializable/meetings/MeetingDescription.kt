package app.meetacy.backend.types.serializable.meetings

import app.meetacy.backend.types.meetings.MeetingDescription
import app.meetacy.backend.types.serializable.serialization
import app.meetacy.backend.types.serializable.meetings.MeetingDescription as MeetingDescriptionSerializable

fun MeetingDescriptionSerializable.type(): MeetingDescription = serialization { MeetingDescription.parse(string) }
fun MeetingDescription.serializable(): MeetingDescriptionSerializable = MeetingDescriptionSerializable(string)

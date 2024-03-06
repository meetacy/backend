package app.meetacy.backend.types.serializable.meetings

import app.meetacy.backend.types.meetings.MeetingIdentity
import app.meetacy.backend.types.serializable.serialization
import app.meetacy.backend.types.serializable.meetings.MeetingId as MeetingIdentitySerializable

fun MeetingIdentitySerializable.type() = serialization { MeetingIdentity.parse(string) }
fun MeetingIdentity.serializable() = MeetingIdentitySerializable(string)

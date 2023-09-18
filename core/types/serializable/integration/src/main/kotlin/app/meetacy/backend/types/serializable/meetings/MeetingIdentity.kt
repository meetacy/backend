package app.meetacy.backend.types.serializable.meetings

import app.meetacy.backend.types.meetings.MeetingIdentity
import app.meetacy.backend.types.serializable.meetings.MeetingIdentity as MeetingIdentitySerializable

fun MeetingIdentitySerializable.type() = MeetingIdentity.parse(string)
fun MeetingIdentity.serializable() = MeetingIdentitySerializable(string)

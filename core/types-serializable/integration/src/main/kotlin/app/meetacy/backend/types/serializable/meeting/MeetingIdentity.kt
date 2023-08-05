package app.meetacy.backend.types.serializable.meeting

import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.types.serializable.meeting.MeetingIdentity as MeetingIdentitySerializable

fun MeetingIdentitySerializable.type() = MeetingIdentity.parse(string)

fun MeetingIdentity.serializable() = MeetingIdentitySerializable(string)

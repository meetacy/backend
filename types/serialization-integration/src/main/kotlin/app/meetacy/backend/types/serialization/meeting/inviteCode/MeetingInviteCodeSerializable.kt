package app.meetacy.backend.types.serialization.meeting.inviteCode

import app.meetacy.backend.types.meeting.inviteCode.MeetingInviteCode
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class MeetingInviteCodeSerializable(private val string: String) {
    fun type() = MeetingInviteCode(string)
}

fun MeetingInviteCode.serializable() = MeetingInviteCodeSerializable(string)

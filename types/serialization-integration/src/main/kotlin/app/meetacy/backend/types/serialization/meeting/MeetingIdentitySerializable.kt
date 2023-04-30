package app.meetacy.backend.types.serialization.meeting

import app.meetacy.backend.types.meeting.MeetingIdentity
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class MeetingIdentitySerializable(val string: String) { // todo иногда приватная иногда нет
    init {
        type()
    }
    fun type() = MeetingIdentity.parse(string)!!
}

fun MeetingIdentity.serializable() = MeetingIdentitySerializable(string)

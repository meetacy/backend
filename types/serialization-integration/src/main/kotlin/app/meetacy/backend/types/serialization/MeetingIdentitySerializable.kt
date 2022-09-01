package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.MeetingIdentity
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class MeetingIdentitySerializable(val string: String) {
    init {
        type()
    }
    fun type() = MeetingIdentity.parse(string)!!
}

fun MeetingIdentity.serializable() = MeetingIdentitySerializable(string)

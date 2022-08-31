package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.MeetingIdentity
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class MeetingIdentitySerializable(val identity: String) {
    init {
        type()
    }
    fun type() = MeetingIdentity.parse(identity)!!
}

fun MeetingIdentity.serializable() = MeetingIdentitySerializable(identity)

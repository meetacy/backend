package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.MeetingId
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
@JvmInline
value class MeetingIdSerializable(private val long: Long) {
    fun type() = MeetingId(long)
}

fun MeetingId.serializable() = MeetingIdSerializable(long)


package app.meetacy.backend.types.serialization.meeting

import app.meetacy.backend.types.meeting.MeetingId
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class MeetingIdSerializable(private val long: Long) {
    fun type() = MeetingId(long)
}

fun MeetingId.serializable() = MeetingIdSerializable(long)


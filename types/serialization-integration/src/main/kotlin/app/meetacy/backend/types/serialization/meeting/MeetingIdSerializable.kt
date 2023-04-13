package app.meetacy.backend.types.serialization.meeting

import app.meetacy.backend.types.meeting.IdMeeting
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class MeetingIdSerializable(private val long: Long) {
    fun type() = IdMeeting(long)
}

fun IdMeeting.serializable() = MeetingIdSerializable(long)


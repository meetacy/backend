package app.meetacy.backend.core.endpoints

import app.meetacy.backend.types.serializable.meetings.MeetingId
import app.meetacy.backend.types.serializable.serialization
import io.ktor.http.*
import kotlinx.serialization.SerializationException

fun Parameters.meetingIdOrNull(name: String = "meetingId"): MeetingId? {
    return this[name]?.let(::MeetingId)
}

fun Parameters.meetingId(name: String = "meetingId"): MeetingId = serialization {
    val meetingId = this[name] ?: throw SerializationException("Bad request. Illegal input: param 'meetingId' is required for type with serial name, but it was missing at path: $name")
    MeetingId(meetingId)
}

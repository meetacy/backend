package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.MeetingId
import kotlinx.serialization.Serializer

@Serializer(MeetingId::class)
object MeetingIdSerializer

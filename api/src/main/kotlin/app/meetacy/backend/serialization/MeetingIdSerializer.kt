package app.meetacy.backend.serialization

import app.meetacy.backend.domain.MeetingId
import kotlinx.serialization.Serializer

@Serializer(MeetingId::class)
object MeetingIdSerializer

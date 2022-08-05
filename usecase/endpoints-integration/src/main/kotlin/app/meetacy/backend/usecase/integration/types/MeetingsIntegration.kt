package app.meetacy.backend.usecase.integration.types

import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.types.serialization.serializable
import app.meetacy.backend.usecase.types.MeetingView

fun MeetingView.mapToEndpoint() = Meeting(
    id = id.serializable(),
    accessHash = accessHash.serializable(),
    creator = creator.mapToEndpoint(),
    date = date.serializable(),
    location = location.serializable(),
    title = title,
    description = description,
    participantsCount = participantsCount,
    isParticipating = isParticipating
)

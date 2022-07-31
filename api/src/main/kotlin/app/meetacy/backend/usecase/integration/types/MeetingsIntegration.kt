package app.meetacy.backend.usecase.integration.types

import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.usecase.types.MeetingView

fun MeetingView.mapToEndpoint() = Meeting(
    id = id,
    accessHash = accessHash,
    creator = creator.mapToDomain(),
    date = date,
    location = location,
    title = title,
    description = description,
    participantsCount = participantsCount,
    isParticipating = isParticipating
)

package app.meetacy.backend.mock.integration.types

import app.meetacy.backend.mock.storage.MockMeeting
import app.meetacy.backend.usecase.types.FullMeeting

fun MockMeeting.mapToUsecase() = FullMeeting(
    identity = identity,
    creatorId = creatorId,
    date = date,
    location = location,
    title = title,
    description = description
)

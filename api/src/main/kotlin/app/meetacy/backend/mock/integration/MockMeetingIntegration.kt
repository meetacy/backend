package app.meetacy.backend.mock.integration

import app.meetacy.backend.mock.storage.MockMeeting
import app.meetacy.backend.usecase.types.FullMeeting

fun MockMeeting.mapToUsecase() = FullMeeting(
    id = id,
    accessHash = accessHash,
    creatorId = creatorId,
    date = date,
    location = location,
    title = title,
    description = description
)

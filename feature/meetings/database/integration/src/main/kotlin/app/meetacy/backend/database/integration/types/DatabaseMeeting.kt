package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.types.DatabaseMeeting
import app.meetacy.backend.types.meetings.FullMeeting

fun DatabaseMeeting.mapToUsecase() = FullMeeting(
    identity = identity,
    creatorId = creatorId,
    date = date,
    location = location,
    title = title,
    description = description,
    avatarId = avatarId,
    visibility = when (visibility) {
        DatabaseMeeting.Visibility.Public -> FullMeeting.Visibility.Public
        DatabaseMeeting.Visibility.Private -> FullMeeting.Visibility.Private
    }
)

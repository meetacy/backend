package app.meetacy.backend.feature.meetings.usecase.integration

import app.meetacy.backend.feature.meetings.database.types.DatabaseMeeting
import app.meetacy.backend.types.meetings.FullMeeting

fun FullMeeting.Visibility.mapToDatabase(): DatabaseMeeting.Visibility =
    when (this) {
        FullMeeting.Visibility.Public -> DatabaseMeeting.Visibility.Public
        FullMeeting.Visibility.Private -> DatabaseMeeting.Visibility.Private
    }

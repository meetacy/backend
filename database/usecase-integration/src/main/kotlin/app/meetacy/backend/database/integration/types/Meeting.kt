package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.types.DatabaseMeeting
import app.meetacy.backend.usecase.types.FullMeeting

fun FullMeeting.Visibility.mapToDatabase(): DatabaseMeeting.Visibility =
    when (this) {
        FullMeeting.Visibility.Public -> DatabaseMeeting.Visibility.Public
        FullMeeting.Visibility.Private -> DatabaseMeeting.Visibility.Private
    }

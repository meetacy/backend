package app.meetacy.backend.infrastructure.database.meetings.view

import app.meetacy.backend.database.integration.meetings.get.DatabaseGetMeetingsViewsViewMeetingsRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.types.meetings.ViewMeetingsRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.viewMeetingRepository: ViewMeetingsRepository by Dependency

fun DIBuilder.viewMeeting() {
    val viewMeetingRepository by singleton<ViewMeetingsRepository> {
        DatabaseGetMeetingsViewsViewMeetingsRepository(database)
    }
}

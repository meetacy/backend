package app.meetacy.backend.infrastructure.database.meetings.get

import app.meetacy.feature.meetings.database.integration.types.DatabaseGetMeetingsViewsRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.getMeetingViewRepository: GetMeetingsViewsRepository by Dependency

fun DIBuilder.getMeeting() {
    val getMeetingViewRepository by singleton<GetMeetingsViewsRepository> {
        DatabaseGetMeetingsViewsRepository(database)
    }
}

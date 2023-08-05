package app.meetacy.backend.infrastructure.integrations.meetings.get

import app.meetacy.backend.database.integration.types.DatabaseGetMeetingsViewsRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.di.builder.DIBuilder

// TODO: replace with new DI
fun DIBuilder.getMeetingsViewsRepository() {
    val getMeetingViewRepository by singleton<GetMeetingsViewsRepository> {
        DatabaseGetMeetingsViewsRepository(database)
    }
}

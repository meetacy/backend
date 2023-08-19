package app.meetacy.backend.application.database.meetings.check

import app.meetacy.feature.meetings.database.integration.meetings.DatabaseCheckMeetingsViewRepository
import app.meetacy.backend.application.database.database
import app.meetacy.backend.types.meetings.CheckMeetingRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.checkMeetingsRepository: CheckMeetingRepository by Dependency

fun DIBuilder.checkMeetings() {
    val checkMeetingsRepository by singleton<CheckMeetingRepository> {
        DatabaseCheckMeetingsViewRepository(database)
    }
}

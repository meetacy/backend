@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.meetings

import app.meetacy.backend.database.integration.meetings.DatabaseCheckMeetingsViewRepository
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.usecase.types.CheckMeetingRepository

val DI.checkMeetingsRepository: CheckMeetingRepository by Dependency

fun DIBuilder.checkMeetingsRepository() {
    val checkMeetingsRepository by singleton<CheckMeetingRepository> {
        DatabaseCheckMeetingsViewRepository(database)
    }
}

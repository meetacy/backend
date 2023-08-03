@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.meetings.get

import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency
import app.meetacy.backend.endpoint.meetings.get.GetMeetingRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.usecase.integration.meetings.get.UsecaseGetMeetingRepository
import app.meetacy.backend.usecase.meetings.get.GetMeetingUsecase

val DI.getMeetingRepository: GetMeetingRepository by Dependency

fun DIBuilder.getMeetingRepository() {
    val getMeetingRepository by singleton<GetMeetingRepository> {
        UsecaseGetMeetingRepository(
            usecase = GetMeetingUsecase(
                authRepository,
                getMeetingsViewsRepository(database)
            )
        )
    }
}

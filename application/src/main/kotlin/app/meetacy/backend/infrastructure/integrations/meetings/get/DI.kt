@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.meetings.get

import app.meetacy.backend.endpoint.meetings.get.GetMeetingRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.usecase.integration.meetings.get.UsecaseGetMeetingRepository
import app.meetacy.backend.usecase.meetings.get.GetMeetingUsecase
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.getMeetingRepository: GetMeetingRepository by Dependency
val DI.getMeetingViewRepository: GetMeetingsViewsRepository by Dependency

fun DIBuilder.getMeetingRepository() {
    getMeetingsViewsRepository()
    val getMeetingRepository by singleton<GetMeetingRepository> {
        UsecaseGetMeetingRepository(
            usecase = GetMeetingUsecase(
                authRepository,
                getMeetingViewRepository
            )
        )
    }
}

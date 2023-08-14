package app.meetacy.backend.infrastructure.usecase.meetings.get

import app.meetacy.backend.endpoint.meetings.get.GetMeetingRepository
import app.meetacy.backend.feature.auth.usecase.integration.meetings.get.UsecaseGetMeetingRepository
import app.meetacy.backend.feature.auth.usecase.meetings.get.GetMeetingUsecase
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.meetings.get.getMeetingViewRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.getMeetingRepository: GetMeetingRepository by Dependency

fun DIBuilder.getMeetingRepository() {
    val getMeetingRepository by singleton<GetMeetingRepository> {
        UsecaseGetMeetingRepository(
            usecase = GetMeetingUsecase(
                authRepository,
                getMeetingViewRepository
            )
        )
    }
}

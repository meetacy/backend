package app.meetacy.backend.infrastructure.usecase.meetings.get

import app.meetacy.backend.feature.meetings.endpoints.get.GetMeetingRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.meetings.get.getMeetingViewRepository
import app.meetacy.backend.feature.meetings.usecase.integration.get.UsecaseGetMeetingRepository
import app.meetacy.backend.feature.meetings.usecase.get.GetMeetingUsecase
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

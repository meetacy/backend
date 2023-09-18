package app.meetacy.backend.feature.meetings.usecase.integration.get

import app.meetacy.backend.feature.meetings.usecase.get.GetMeetingUsecase
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.getMeetingUsecase() {
    val getMeetingUsecase by singleton {
        val authRepository: AuthRepository by getting
        val getMeetingsViewsRepository: GetMeetingsViewsRepository by getting

        GetMeetingUsecase(
            authRepository = authRepository,
            getMeetingsViewsRepository = getMeetingsViewsRepository
        )
    }
}

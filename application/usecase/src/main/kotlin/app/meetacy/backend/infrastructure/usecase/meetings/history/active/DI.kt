package app.meetacy.backend.infrastructure.usecase.meetings.history.active

import app.meetacy.backend.endpoint.meetings.history.active.ListMeetingsActiveRepository
import app.meetacy.backend.feature.auth.usecase.integration.meetings.history.active.UsecaseListActiveMeetingsRepository
import app.meetacy.backend.feature.auth.usecase.meetings.history.active.ListMeetingsActiveUsecase
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.meetings.get.getMeetingViewRepository
import app.meetacy.backend.infrastructure.database.meetings.history.active.listActiveMeetingsStorage
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.listActiveMeetingsRepository: ListMeetingsActiveRepository by Dependency

fun DIBuilder.listActiveMeetingsRepository() {
    val listActiveMeetingsRepository by singleton<ListMeetingsActiveRepository> {
        UsecaseListActiveMeetingsRepository(
            usecase = ListMeetingsActiveUsecase(
                authRepository,
                listActiveMeetingsStorage,
                getMeetingViewRepository
            )
        )
    }
}

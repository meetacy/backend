package app.meetacy.backend.application.usecase.meetings.history.active

import app.meetacy.backend.feature.meetings.endpoints.history.active.ListMeetingsActiveRepository
import app.meetacy.backend.application.database.meetings.get.getMeetingViewRepository
import app.meetacy.backend.application.database.meetings.history.active.listActiveMeetingsStorage
import app.meetacy.backend.feature.meetings.usecase.integration.history.active.UsecaseListActiveMeetingsRepository
import app.meetacy.backend.feature.meetings.usecase.history.active.ListMeetingsActiveUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.listActiveMeetingsRepository: ListMeetingsActiveRepository by Dependency

fun DIBuilder.listActiveMeetingsRepository() {
    val listActiveMeetingsRepository by singleton<ListMeetingsActiveRepository> {
        UsecaseListActiveMeetingsRepository(
            usecase = ListMeetingsActiveUsecase(
                authRepository = get(),
                listActiveMeetingsStorage,
                getMeetingViewRepository
            )
        )
    }
}

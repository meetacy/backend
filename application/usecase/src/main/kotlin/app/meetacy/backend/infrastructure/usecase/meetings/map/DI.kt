package app.meetacy.backend.infrastructure.usecase.meetings.map

import app.meetacy.backend.feature.meetings.endpoints.map.list.ListMeetingsMapRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.meetings.get.getMeetingViewRepository
import app.meetacy.backend.infrastructure.database.meetings.map.listMeetingsMapListStorage
import app.meetacy.backend.infrastructure.database.meetings.view.viewMeetingRepository
import app.meetacy.backend.feature.meetings.usecase.integration.map.list.UsecaseListMeetingsMapRepository
import app.meetacy.backend.feature.meetings.usecase.map.list.ListMeetingsMapUsecase
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.listMeetingsMapRepository() {
    val listMeetingsMapRepository by singleton<ListMeetingsMapRepository> {
        UsecaseListMeetingsMapRepository(
            usecase = ListMeetingsMapUsecase(
                authRepository = authRepository,
                storage = listMeetingsMapListStorage,
                getMeetingsViewsRepository = getMeetingViewRepository,
                viewMeetingsRepository = viewMeetingRepository
            )
        )
    }
}

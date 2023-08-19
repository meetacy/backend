package app.meetacy.backend.application.endpoints.meetings.map

import app.meetacy.backend.feature.meetings.endpoints.map.list.ListMeetingsMapRepository
import app.meetacy.backend.application.database.meetings.get.getMeetingViewRepository
import app.meetacy.backend.application.database.meetings.map.listMeetingsMapListStorage
import app.meetacy.backend.application.database.meetings.view.viewMeetingRepository
import app.meetacy.backend.feature.meetings.usecase.integration.map.list.UsecaseListMeetingsMapRepository
import app.meetacy.backend.feature.meetings.usecase.map.list.ListMeetingsMapUsecase
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.listMeetingsMapRepository() {
    val listMeetingsMapRepository by singleton<ListMeetingsMapRepository> {
        UsecaseListMeetingsMapRepository(
            usecase = ListMeetingsMapUsecase(
                authRepository = get(),
                storage = listMeetingsMapListStorage,
                getMeetingsViewsRepository = getMeetingViewRepository,
                viewMeetingsRepository = viewMeetingRepository
            )
        )
    }
}

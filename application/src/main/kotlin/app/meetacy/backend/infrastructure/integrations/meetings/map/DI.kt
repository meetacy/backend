package app.meetacy.backend.infrastructure.integrations.meetings.map

import app.meetacy.backend.endpoint.meetings.map.MeetingsMapDependencies
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.meetings.get.getMeetingViewRepository
import app.meetacy.backend.infrastructure.database.meetings.map.listMeetingsMapListStorage
import app.meetacy.backend.infrastructure.database.meetings.view.viewMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.map.list.UsecaseListMeetingsMapRepository
import app.meetacy.backend.usecase.meetings.map.list.ListMeetingsMapUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.meetingsMapDependencies: MeetingsMapDependencies by Dependency

fun DIBuilder.meetingsMapDependencies() {
    val meetingsMapDependencies by singleton<MeetingsMapDependencies> {
        MeetingsMapDependencies(
            listMeetingsMapRepository = UsecaseListMeetingsMapRepository(
                usecase = ListMeetingsMapUsecase(
                    authRepository = authRepository,
                    storage = listMeetingsMapListStorage,
                    getMeetingsViewsRepository = getMeetingViewRepository,
                    viewMeetingsRepository = viewMeetingRepository
                )
            )
        )
    }
}

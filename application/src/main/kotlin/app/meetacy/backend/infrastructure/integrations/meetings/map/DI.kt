@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.meetings.map

import app.meetacy.backend.database.integration.meetings.map.list.DatabaseListMeetingsMapListStorage
import app.meetacy.backend.endpoint.meetings.map.MeetingsMapDependencies
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.meetings.create.viewMeetingRepository
import app.meetacy.backend.infrastructure.integrations.meetings.get.getMeetingViewRepository
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
                    storage = DatabaseListMeetingsMapListStorage(database),
                    getMeetingsViewsRepository = getMeetingViewRepository,
                    viewMeetingsRepository = viewMeetingRepository
                )
            )
        )
    }
}

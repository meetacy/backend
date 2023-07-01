package app.meetacy.backend.infrastructure.factories.meetings.map

import app.meetacy.backend.database.integration.meetings.map.list.DatabaseListMeetingsMapListStorage
import app.meetacy.backend.endpoint.meetings.map.MeetingsMapDependencies
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.meetings.get.getMeetingsViewsRepository
import app.meetacy.backend.infrastructure.factories.meetings.get.viewMeetingsRepository
import app.meetacy.backend.usecase.integration.meetings.map.list.UsecaseListMeetingsMapRepository
import app.meetacy.backend.usecase.meetings.map.list.ListMeetingsMapUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.ViewMeetingsRepository
import org.jetbrains.exposed.sql.Database

fun meetingsMapDependencies(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    getMeetingsViewsRepository: GetMeetingsViewsRepository = getMeetingsViewsRepository(db),
    viewMeetingsRepository: ViewMeetingsRepository = viewMeetingsRepository(db)
): MeetingsMapDependencies = MeetingsMapDependencies(
    listMeetingsMapRepository = UsecaseListMeetingsMapRepository(
        usecase = ListMeetingsMapUsecase(
            authRepository = authRepository,
            storage = DatabaseListMeetingsMapListStorage(db),
            getMeetingsViewsRepository = getMeetingsViewsRepository,
            viewMeetingsRepository = viewMeetingsRepository
        )
    )
)

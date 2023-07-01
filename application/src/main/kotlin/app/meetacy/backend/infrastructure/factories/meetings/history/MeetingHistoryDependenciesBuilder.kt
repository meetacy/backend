package app.meetacy.backend.infrastructure.factories.meetings.history

import app.meetacy.backend.database.integration.meetings.history.active.DatabaseListActiveMeetingsStorage
import app.meetacy.backend.database.integration.meetings.history.list.DatabaseListMeetingsHistoryListStorage
import app.meetacy.backend.database.integration.meetings.history.past.DatabaseListPastMeetingsStorage
import app.meetacy.backend.endpoint.meetings.history.MeetingsHistoryDependencies
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.meetings.getMeetingsViewsRepository
import app.meetacy.backend.usecase.integration.meetings.history.active.UsecaseListActiveMeetingsRepository
import app.meetacy.backend.usecase.integration.meetings.history.list.UsecaseListMeetingsHistoryRepository
import app.meetacy.backend.usecase.integration.meetings.history.past.UsecaseListPastMeetingsRepository
import app.meetacy.backend.usecase.meetings.history.active.ListMeetingsActiveUsecase
import app.meetacy.backend.usecase.meetings.history.list.ListMeetingsHistoryUsecase
import app.meetacy.backend.usecase.meetings.history.past.ListMeetingsPastUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import org.jetbrains.exposed.sql.Database

fun meetingHistoryDependencies(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    getMeetingsViewsRepository: GetMeetingsViewsRepository = getMeetingsViewsRepository(db)
): MeetingsHistoryDependencies = MeetingsHistoryDependencies(
    listMeetingsHistoryRepository = UsecaseListMeetingsHistoryRepository(
        usecase = ListMeetingsHistoryUsecase(
            authRepository = authRepository,
            storage = DatabaseListMeetingsHistoryListStorage(db),
            getMeetingsViewsRepository = getMeetingsViewsRepository
        )
    ),
    meetingsActiveRepository = UsecaseListActiveMeetingsRepository(
        usecase = ListMeetingsActiveUsecase(
            authRepository,
            storage = DatabaseListActiveMeetingsStorage(db),
            getMeetingsViewsRepository = getMeetingsViewsRepository
        )
    ),
    meetingsPastRepository = UsecaseListPastMeetingsRepository(
        usecase = ListMeetingsPastUsecase(
            authRepository,
            storage = DatabaseListPastMeetingsStorage(db),
            getMeetingsViewsRepository = getMeetingsViewsRepository
        )
    ),
)

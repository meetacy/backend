package app.meetacy.backend.infrastructure.factories.meetings.history

import app.meetacy.backend.database.integration.meetings.history.past.DatabaseListPastMeetingsStorage
import app.meetacy.backend.endpoint.meetings.history.MeetingsHistoryDependencies
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.meetings.getMeetingsViewsRepository
import app.meetacy.backend.usecase.integration.meetings.history.past.UsecaseListPastMeetingsRepository
import app.meetacy.backend.usecase.meetings.history.past.ListMeetingsPastUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import org.jetbrains.exposed.sql.Database

fun meetingHistoryDependencies(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    getMeetingsViewsRepository: GetMeetingsViewsRepository = getMeetingsViewsRepository(db)
): MeetingsHistoryDependencies = MeetingsHistoryDependencies(
    listMeetingsHistoryRepository = listMeetingsHistoryRepository(db),
    meetingsActiveRepository = listActiveMeetingsRepository(db),
    meetingsPastRepository = UsecaseListPastMeetingsRepository(
        usecase = ListMeetingsPastUsecase(
            authRepository,
            storage = DatabaseListPastMeetingsStorage(db),
            getMeetingsViewsRepository = getMeetingsViewsRepository
        )
    ),
)

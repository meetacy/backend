package app.meetacy.backend.infrastructure.factories.meetings.history

import app.meetacy.backend.database.integration.meetings.history.list.DatabaseListMeetingsHistoryListStorage
import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsHistoryRepository
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.meetings.getMeetingsViewsRepository
import app.meetacy.backend.usecase.integration.meetings.history.list.UsecaseListMeetingsHistoryRepository
import app.meetacy.backend.usecase.meetings.history.list.ListMeetingsHistoryUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import org.jetbrains.exposed.sql.Database

fun listMeetingsHistoryRepository(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    getMeetingsViewsRepository: GetMeetingsViewsRepository = getMeetingsViewsRepository(db)
): ListMeetingsHistoryRepository = UsecaseListMeetingsHistoryRepository(
    usecase = ListMeetingsHistoryUsecase(
        authRepository = authRepository,
        storage = DatabaseListMeetingsHistoryListStorage(db),
        getMeetingsViewsRepository = getMeetingsViewsRepository
    )
)

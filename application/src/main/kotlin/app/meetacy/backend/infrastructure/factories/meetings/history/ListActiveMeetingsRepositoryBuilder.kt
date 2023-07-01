package app.meetacy.backend.infrastructure.factories.meetings.history

import app.meetacy.backend.database.integration.meetings.history.active.DatabaseListActiveMeetingsStorage
import app.meetacy.backend.endpoint.meetings.history.active.ListMeetingsActiveRepository
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.meetings.getMeetingsViewsRepository
import app.meetacy.backend.usecase.integration.meetings.history.active.UsecaseListActiveMeetingsRepository
import app.meetacy.backend.usecase.meetings.history.active.ListMeetingsActiveUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import org.jetbrains.exposed.sql.Database

fun listActiveMeetingsRepository(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    getMeetingsViewsRepository: GetMeetingsViewsRepository = getMeetingsViewsRepository(db)
): ListMeetingsActiveRepository = UsecaseListActiveMeetingsRepository(
    usecase = ListMeetingsActiveUsecase(
        authRepository,
        storage = DatabaseListActiveMeetingsStorage(db),
        getMeetingsViewsRepository = getMeetingsViewsRepository
    )
)

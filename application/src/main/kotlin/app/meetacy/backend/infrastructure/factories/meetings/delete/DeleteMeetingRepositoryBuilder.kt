package app.meetacy.backend.infrastructure.factories.meetings.delete

import app.meetacy.backend.database.integration.meetings.delete.DatabaseDeleteMeetingStorage
import app.meetacy.backend.endpoint.meetings.delete.DeleteMeetingRepository
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.meetings.get.getMeetingsViewsRepository
import app.meetacy.backend.usecase.integration.meetings.delete.UsecaseDeleteMeetingRepository
import app.meetacy.backend.usecase.meetings.delete.DeleteMeetingUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import org.jetbrains.exposed.sql.Database

fun deleteMeetingRepository(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    getMeetingsViewsRepository: GetMeetingsViewsRepository = getMeetingsViewsRepository(db)
): DeleteMeetingRepository = UsecaseDeleteMeetingRepository(
    usecase = DeleteMeetingUsecase(
        authRepository = authRepository,
        getMeetingsViewsRepository = getMeetingsViewsRepository,
        storage = DatabaseDeleteMeetingStorage(db)
    )
)

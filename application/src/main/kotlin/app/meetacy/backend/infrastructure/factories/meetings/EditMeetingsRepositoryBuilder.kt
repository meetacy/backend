package app.meetacy.backend.infrastructure.factories.meetings

import app.meetacy.backend.database.integration.meetings.edit.DatabaseEditMeetingStorage
import app.meetacy.backend.endpoint.meetings.edit.EditMeetingRepository
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.files.filesRepository
import app.meetacy.backend.usecase.integration.meetings.edit.UsecaseEditMeetingRepository
import app.meetacy.backend.usecase.meetings.edit.EditMeetingUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.FilesRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.ViewMeetingsRepository
import app.meetacy.backend.utf8.integration.DefaultUtf8Checker
import org.jetbrains.exposed.sql.Database

fun editMeetingRepository(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    getMeetingsViewsRepository: GetMeetingsViewsRepository = getMeetingsViewsRepository(db),
    viewMeetingsRepository: ViewMeetingsRepository = viewMeetingsRepository(db),
    filesRepository: FilesRepository = filesRepository(db)
): EditMeetingRepository = UsecaseEditMeetingRepository(
    usecase = EditMeetingUsecase(
        storage = DatabaseEditMeetingStorage(db),
        authRepository = authRepository,
        getMeetingsViewsRepository = getMeetingsViewsRepository,
        viewMeetingsRepository = viewMeetingsRepository,
        filesRepository = filesRepository,
        utf8Checker = DefaultUtf8Checker
    )
)

package app.meetacy.backend.infrastructure.factories.meetings

import app.meetacy.backend.database.integration.meetings.create.DatabaseCreateMeetingStorage
import app.meetacy.backend.database.integration.meetings.create.DatabaseCreateMeetingViewMeetingRepository
import app.meetacy.backend.endpoint.meetings.create.CreateMeetingRepository
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.files.filesRepository
import app.meetacy.backend.usecase.integration.meetings.create.UsecaseCreateMeetingRepository
import app.meetacy.backend.usecase.meetings.create.CreateMeetingUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.FilesRepository
import app.meetacy.backend.utf8.integration.DefaultUtf8Checker
import org.jetbrains.exposed.sql.Database

fun createMeetingRepository(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    filesRepository: FilesRepository = filesRepository(db)
): CreateMeetingRepository = UsecaseCreateMeetingRepository(
    usecase = CreateMeetingUsecase(
        hashGenerator = DefaultHashGenerator,
        storage = DatabaseCreateMeetingStorage(db),
        authRepository = authRepository,
        viewMeetingRepository = DatabaseCreateMeetingViewMeetingRepository(db),
        utf8Checker = DefaultUtf8Checker,
        filesRepository = filesRepository
    )
)

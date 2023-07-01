package app.meetacy.backend.infrastructure.factories.meetings

import app.meetacy.backend.database.integration.meetings.delete.DatabaseDeleteMeetingStorage
import app.meetacy.backend.database.integration.meetings.edit.DatabaseEditMeetingStorage
import app.meetacy.backend.endpoint.meetings.MeetingsDependencies
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.files.filesRepository
import app.meetacy.backend.infrastructure.factories.meetings.history.meetingHistoryDependencies
import app.meetacy.backend.usecase.integration.meetings.delete.UsecaseDeleteMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.edit.UsecaseEditMeetingRepository
import app.meetacy.backend.usecase.meetings.delete.DeleteMeetingUsecase
import app.meetacy.backend.usecase.meetings.edit.EditMeetingUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.FilesRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.ViewMeetingsRepository
import app.meetacy.backend.utf8.integration.DefaultUtf8Checker
import org.jetbrains.exposed.sql.Database

fun meetingsDependenciesFactory(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    filesRepository: FilesRepository = filesRepository(db),
    getMeetingsViewsRepository: GetMeetingsViewsRepository = getMeetingsViewsRepository(db),
    viewMeetingsRepository: ViewMeetingsRepository = viewMeetingsRepository(db)
): MeetingsDependencies = MeetingsDependencies(
    meetingsHistoryDependencies = meetingHistoryDependencies(db),
    meetingsMapDependencies = meetingsMapDependencies(db),
    meetingParticipantsDependencies = participantDependenciesBuilder(db),
    createMeetingRepository = createMeetingRepository(db),
    getMeetingRepository = getMeetingRepository(db),
    participateMeetingRepository = participateMeetingRepository(db),
    deleteMeetingRepository = UsecaseDeleteMeetingRepository(
        usecase = DeleteMeetingUsecase(
            authRepository = authRepository,
            getMeetingsViewsRepository = getMeetingsViewsRepository,
            storage = DatabaseDeleteMeetingStorage(db)
        )
    ),
    editMeetingRepository = UsecaseEditMeetingRepository(
        usecase = EditMeetingUsecase(
            storage = DatabaseEditMeetingStorage(db),
            authRepository = authRepository,
            getMeetingsViewsRepository = getMeetingsViewsRepository,
            viewMeetingsRepository = viewMeetingsRepository,
            filesRepository = filesRepository,
            utf8Checker = DefaultUtf8Checker
        )
    )
)

package app.meetacy.backend.infrastructure.factories.meetings

import app.meetacy.backend.database.integration.meetings.create.DatabaseCreateMeetingStorage
import app.meetacy.backend.database.integration.meetings.create.DatabaseCreateMeetingViewMeetingRepository
import app.meetacy.backend.database.integration.meetings.delete.DatabaseDeleteMeetingStorage
import app.meetacy.backend.database.integration.meetings.edit.DatabaseEditMeetingStorage
import app.meetacy.backend.database.integration.meetings.participants.list.DatabaseListMeetingParticipantsStorage
import app.meetacy.backend.database.integration.meetings.participate.DatabaseParticipateMeetingStorage
import app.meetacy.backend.endpoint.meetings.MeetingsDependencies
import app.meetacy.backend.endpoint.meetings.participants.ParticipantsDependencies
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.files.filesRepository
import app.meetacy.backend.infrastructure.factories.meetings.history.meetingHistoryDependencies
import app.meetacy.backend.infrastructure.factories.users.getUserViewsRepository
import app.meetacy.backend.usecase.integration.meetings.create.UsecaseCreateMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.delete.UsecaseDeleteMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.edit.UsecaseEditMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.get.UsecaseGetMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.participants.list.UsecaseListMeetingParticipantsRepository
import app.meetacy.backend.usecase.integration.meetings.participate.UsecaseParticipateMeetingRepository
import app.meetacy.backend.usecase.meetings.create.CreateMeetingUsecase
import app.meetacy.backend.usecase.meetings.delete.DeleteMeetingUsecase
import app.meetacy.backend.usecase.meetings.edit.EditMeetingUsecase
import app.meetacy.backend.usecase.meetings.get.GetMeetingUsecase
import app.meetacy.backend.usecase.meetings.participants.list.ListMeetingParticipantsUsecase
import app.meetacy.backend.usecase.meetings.participate.ParticipateMeetingUsecase
import app.meetacy.backend.usecase.types.*
import app.meetacy.backend.utf8.integration.DefaultUtf8Checker
import org.jetbrains.exposed.sql.Database

fun meetingsDependenciesFactory(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    filesRepository: FilesRepository = filesRepository(db),
    checkMeetingsRepository: CheckMeetingRepository = checkMeetingsRepository(db),
    getMeetingsViewsRepository: GetMeetingsViewsRepository = getMeetingsViewsRepository(db),
    getUsersViewsRepository: GetUsersViewsRepository = getUserViewsRepository(db),
    viewMeetingsRepository: ViewMeetingsRepository = viewMeetingsRepository(db)
): MeetingsDependencies = MeetingsDependencies(
    meetingsHistoryDependencies = meetingHistoryDependencies(db),
    meetingsMapDependencies = meetingsMapDependencies(db),
    meetingParticipantsDependencies = ParticipantsDependencies(
        listMeetingParticipantsRepository = UsecaseListMeetingParticipantsRepository(
            usecase = ListMeetingParticipantsUsecase(
                authRepository = authRepository,
                checkMeetingRepository = checkMeetingsRepository,
                storage = DatabaseListMeetingParticipantsStorage(db),
                getUsersViewsRepository = getUsersViewsRepository
            )
        )
    ),
    createMeetingRepository = UsecaseCreateMeetingRepository(
        usecase = CreateMeetingUsecase(
            hashGenerator = DefaultHashGenerator,
            storage = DatabaseCreateMeetingStorage(db),
            authRepository = authRepository,
            viewMeetingRepository = DatabaseCreateMeetingViewMeetingRepository(db),
            utf8Checker = DefaultUtf8Checker,
            filesRepository = filesRepository
        )
    ),
    getMeetingRepository = UsecaseGetMeetingRepository(
        usecase = GetMeetingUsecase(
            authRepository = authRepository,
            getMeetingsViewsRepository = getMeetingsViewsRepository
        )
    ),
    participateMeetingRepository = UsecaseParticipateMeetingRepository(
        usecase = ParticipateMeetingUsecase(
            authRepository = authRepository,
            storage = DatabaseParticipateMeetingStorage(db),
            getMeetingsViewsRepository = getMeetingsViewsRepository
        )
    ),
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

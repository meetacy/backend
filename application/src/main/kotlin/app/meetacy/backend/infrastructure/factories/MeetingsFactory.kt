package app.meetacy.backend.infrastructure.factories

import app.meetacy.backend.database.integration.meetings.create.DatabaseCreateMeetingStorage
import app.meetacy.backend.database.integration.meetings.create.DatabaseCreateMeetingViewMeetingRepository
import app.meetacy.backend.database.integration.meetings.delete.DatabaseDeleteMeetingStorage
import app.meetacy.backend.database.integration.meetings.edit.DatabaseEditMeetingStorage
import app.meetacy.backend.database.integration.meetings.history.active.DatabaseListActiveMeetingsStorage
import app.meetacy.backend.database.integration.meetings.history.list.DatabaseListMeetingsHistoryListStorage
import app.meetacy.backend.database.integration.meetings.history.past.DatabaseListPastMeetingsStorage
import app.meetacy.backend.database.integration.meetings.map.list.DatabaseListMeetingsMapListStorage
import app.meetacy.backend.database.integration.meetings.participants.list.DatabaseListMeetingParticipantsStorage
import app.meetacy.backend.database.integration.meetings.participate.DatabaseParticipateMeetingStorage
import app.meetacy.backend.endpoint.meetings.MeetingsDependencies
import app.meetacy.backend.endpoint.meetings.history.MeetingsHistoryDependencies
import app.meetacy.backend.endpoint.meetings.map.MeetingsMapDependencies
import app.meetacy.backend.endpoint.meetings.participants.ParticipantsDependencies
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.usecase.integration.meetings.create.UsecaseCreateMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.delete.UsecaseDeleteMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.edit.UsecaseEditMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.get.UsecaseGetMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.history.active.UsecaseListActiveMeetingsRepository
import app.meetacy.backend.usecase.integration.meetings.history.list.UsecaseListMeetingsHistoryRepository
import app.meetacy.backend.usecase.integration.meetings.history.past.UsecaseListPastMeetingsRepository
import app.meetacy.backend.usecase.integration.meetings.map.list.UsecaseListMeetingsMapRepository
import app.meetacy.backend.usecase.integration.meetings.participants.list.UsecaseListMeetingParticipantsRepository
import app.meetacy.backend.usecase.integration.meetings.participate.UsecaseParticipateMeetingRepository
import app.meetacy.backend.usecase.meetings.create.CreateMeetingUsecase
import app.meetacy.backend.usecase.meetings.delete.DeleteMeetingUsecase
import app.meetacy.backend.usecase.meetings.edit.EditMeetingUsecase
import app.meetacy.backend.usecase.meetings.get.GetMeetingUsecase
import app.meetacy.backend.usecase.meetings.history.active.ListMeetingsActiveUsecase
import app.meetacy.backend.usecase.meetings.history.list.ListMeetingsHistoryUsecase
import app.meetacy.backend.usecase.meetings.history.past.ListMeetingsPastUsecase
import app.meetacy.backend.usecase.meetings.map.list.ListMeetingsMapUsecase
import app.meetacy.backend.usecase.meetings.participants.list.ListMeetingParticipantsUsecase
import app.meetacy.backend.usecase.meetings.participate.ParticipateMeetingUsecase
import app.meetacy.backend.usecase.types.*
import app.meetacy.backend.utf8.integration.DefaultUtf8Checker
import org.jetbrains.exposed.sql.Database

fun meetingsDependenciesFactory(
    db: Database,
    authRepository: AuthRepository,
    filesRepository: FilesRepository,
    checkMeetingsRepository: CheckMeetingRepository,
    getMeetingsViewsRepository: GetMeetingsViewsRepository,
    getUsersViewsRepository: GetUsersViewsRepository,
    viewMeetingsRepository: ViewMeetingsRepository
): MeetingsDependencies = MeetingsDependencies(
    meetingsHistoryDependencies = MeetingsHistoryDependencies(
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
    ),
    meetingsMapDependencies = MeetingsMapDependencies(
        listMeetingsMapRepository = UsecaseListMeetingsMapRepository(
            usecase = ListMeetingsMapUsecase(
                authRepository = authRepository,
                storage = DatabaseListMeetingsMapListStorage(db),
                getMeetingsViewsRepository = getMeetingsViewsRepository,
                viewMeetingsRepository = viewMeetingsRepository
            )
        )
    ),
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

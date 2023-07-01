package app.meetacy.backend.infrastructure.factories.meetings.participate

import app.meetacy.backend.database.integration.meetings.participants.list.DatabaseListMeetingParticipantsStorage
import app.meetacy.backend.endpoint.meetings.participants.ParticipantsDependencies
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.meetings.checkMeetingsRepository
import app.meetacy.backend.infrastructure.factories.users.get.getUserViewsRepository
import app.meetacy.backend.usecase.integration.meetings.participants.list.UsecaseListMeetingParticipantsRepository
import app.meetacy.backend.usecase.meetings.participants.list.ListMeetingParticipantsUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.CheckMeetingRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import org.jetbrains.exposed.sql.Database

fun participantDependenciesBuilder(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    checkMeetingRepository: CheckMeetingRepository = checkMeetingsRepository(db),
    getUsersViewsRepository: GetUsersViewsRepository = getUserViewsRepository(db)
): ParticipantsDependencies = ParticipantsDependencies(
    listMeetingParticipantsRepository = UsecaseListMeetingParticipantsRepository(
        usecase = ListMeetingParticipantsUsecase(
            authRepository = authRepository,
            checkMeetingRepository = checkMeetingRepository,
            storage = DatabaseListMeetingParticipantsStorage(db),
            getUsersViewsRepository = getUsersViewsRepository
        )
    )
)

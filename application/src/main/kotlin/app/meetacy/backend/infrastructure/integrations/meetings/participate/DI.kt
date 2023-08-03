@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.meetings.participate

import app.meetacy.backend.database.integration.meetings.participants.list.DatabaseListMeetingParticipantsStorage
import app.meetacy.backend.database.integration.meetings.participate.DatabaseParticipateMeetingStorage
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency
import app.meetacy.backend.endpoint.meetings.participants.ParticipantsDependencies
import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.integrations.meetings.checkMeetingsRepository
import app.meetacy.backend.infrastructure.integrations.meetings.get.getMeetingsViewsRepository
import app.meetacy.backend.infrastructure.integrations.users.get.getUserViewsRepository
import app.meetacy.backend.usecase.integration.meetings.participants.list.UsecaseListMeetingParticipantsRepository
import app.meetacy.backend.usecase.integration.meetings.participate.UsecaseParticipateMeetingRepository
import app.meetacy.backend.usecase.meetings.participants.list.ListMeetingParticipantsUsecase
import app.meetacy.backend.usecase.meetings.participate.ParticipateMeetingUsecase

val DI.participateMeetingRepository: ParticipateMeetingRepository by Dependency
val DI.participantDependencies: ParticipantsDependencies by Dependency

fun DIBuilder.participateMeetingRepository() {
    val participateMeetingRepository by singleton<ParticipateMeetingRepository> {
        UsecaseParticipateMeetingRepository(
            usecase = ParticipateMeetingUsecase(
                authRepository,
                DatabaseParticipateMeetingStorage(database),
                getMeetingsViewsRepository(database)
            )
        )
    }
    val participantDependencies by singleton<ParticipantsDependencies> {
        ParticipantsDependencies(
            listMeetingParticipantsRepository = UsecaseListMeetingParticipantsRepository(
                ListMeetingParticipantsUsecase(
                    authRepository,
                    checkMeetingsRepository,
                    DatabaseListMeetingParticipantsStorage(database),
                    getUserViewsRepository
                )
            )
        )
    }
}

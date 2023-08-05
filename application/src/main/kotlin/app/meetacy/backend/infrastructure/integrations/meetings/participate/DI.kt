package app.meetacy.backend.infrastructure.integrations.meetings.participate

import app.meetacy.backend.endpoint.meetings.participants.ParticipantsDependencies
import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.meetings.check.checkMeetingsRepository
import app.meetacy.backend.infrastructure.database.meetings.get.getMeetingViewRepository
import app.meetacy.backend.infrastructure.database.meetings.participate.listMeetingParticipantsStorage
import app.meetacy.backend.infrastructure.database.meetings.participate.participateMeetingStorage
import app.meetacy.backend.infrastructure.database.users.get.getUserViewsRepository
import app.meetacy.backend.usecase.integration.meetings.participants.list.UsecaseListMeetingParticipantsRepository
import app.meetacy.backend.usecase.integration.meetings.participate.UsecaseParticipateMeetingRepository
import app.meetacy.backend.usecase.meetings.participants.list.ListMeetingParticipantsUsecase
import app.meetacy.backend.usecase.meetings.participate.ParticipateMeetingUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.participateMeetingRepository: ParticipateMeetingRepository by Dependency
val DI.participantDependencies: ParticipantsDependencies by Dependency

fun DIBuilder.participateMeetingRepository() {
    val participateMeetingRepository by singleton<ParticipateMeetingRepository> {
        UsecaseParticipateMeetingRepository(
            usecase = ParticipateMeetingUsecase(
                authRepository,
                participateMeetingStorage,
                getMeetingViewRepository
            )
        )
    }
    val participantDependencies by singleton<ParticipantsDependencies> {
        ParticipantsDependencies(
            listMeetingParticipantsRepository = UsecaseListMeetingParticipantsRepository(
                ListMeetingParticipantsUsecase(
                    authRepository,
                    checkMeetingsRepository,
                    listMeetingParticipantsStorage,
                    getUserViewsRepository
                )
            )
        )
    }
}

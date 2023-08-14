package app.meetacy.backend.infrastructure.usecase.meetings.participate

import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingRepository
import app.meetacy.backend.feature.auth.usecase.integration.meetings.participate.UsecaseParticipateMeetingRepository
import app.meetacy.backend.feature.auth.usecase.meetings.participate.ParticipateMeetingUsecase
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.meetings.get.getMeetingViewRepository
import app.meetacy.backend.infrastructure.database.meetings.participate.participateMeetingStorage
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.participateMeetingRepository: ParticipateMeetingRepository by Dependency

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
}

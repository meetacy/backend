package app.meetacy.backend.application.usecase.meetings.participate

import app.meetacy.backend.feature.meetings.endpoints.participate.ParticipateMeetingRepository
import app.meetacy.backend.application.database.meetings.get.getMeetingViewRepository
import app.meetacy.backend.application.database.meetings.participate.participateMeetingStorage
import app.meetacy.backend.feature.meetings.usecase.integration.participate.UsecaseParticipateMeetingRepository
import app.meetacy.backend.feature.meetings.usecase.participate.ParticipateMeetingUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.participateMeetingRepository: ParticipateMeetingRepository by Dependency

fun DIBuilder.participateMeetingRepository() {
    val participateMeetingRepository by singleton<ParticipateMeetingRepository> {
        UsecaseParticipateMeetingRepository(
            usecase = ParticipateMeetingUsecase(
                authRepository = get(),
                participateMeetingStorage,
                getMeetingViewRepository
            )
        )
    }
}

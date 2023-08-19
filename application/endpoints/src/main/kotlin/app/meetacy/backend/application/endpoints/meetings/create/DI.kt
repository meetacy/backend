package app.meetacy.backend.application.endpoints.meetings.create

import app.meetacy.backend.feature.meetings.endpoints.create.CreateMeetingRepository
import app.meetacy.backend.application.database.files.filesRepository
import app.meetacy.backend.application.database.meetings.create.createMeetingStorage
import app.meetacy.backend.application.database.meetings.create.viewMeetingRepository
import app.meetacy.backend.feature.meetings.usecase.integration.create.UsecaseCreateMeetingRepository
import app.meetacy.backend.feature.meetings.usecase.create.CreateMeetingUsecase
import app.meetacy.backend.types.integration.utf8Checker.DefaultUtf8Checker
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.createMeetingRepository: CreateMeetingRepository by Dependency


fun DIBuilder.createMeetingRepository() {
    val createMeetingRepository by singleton<CreateMeetingRepository> {
        UsecaseCreateMeetingRepository(
            usecase = CreateMeetingUsecase(
                hashGenerator = get(),
                storage = createMeetingStorage,
                authRepository = get(),
                viewMeetingRepository = viewMeetingRepository,
                utf8Checker = DefaultUtf8Checker,
                filesRepository = filesRepository
            )
        )
    }
}

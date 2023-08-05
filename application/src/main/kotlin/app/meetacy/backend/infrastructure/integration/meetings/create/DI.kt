package app.meetacy.backend.infrastructure.integration.meetings.create

import app.meetacy.backend.endpoint.meetings.create.CreateMeetingRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.files.filesRepository
import app.meetacy.backend.infrastructure.database.meetings.create.createMeetingStorage
import app.meetacy.backend.infrastructure.database.meetings.create.viewMeetingRepository
import app.meetacy.backend.usecase.integration.meetings.create.UsecaseCreateMeetingRepository
import app.meetacy.backend.usecase.meetings.create.CreateMeetingUsecase
import app.meetacy.backend.utf8.integration.DefaultUtf8Checker
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
                authRepository = authRepository,
                viewMeetingRepository = viewMeetingRepository,
                utf8Checker = DefaultUtf8Checker,
                filesRepository = filesRepository
            )
        )
    }
}

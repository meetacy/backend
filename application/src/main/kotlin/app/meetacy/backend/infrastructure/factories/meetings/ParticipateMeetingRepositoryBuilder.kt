package app.meetacy.backend.infrastructure.factories.meetings

import app.meetacy.backend.database.integration.meetings.participate.DatabaseParticipateMeetingStorage
import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingRepository
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.usecase.integration.meetings.participate.UsecaseParticipateMeetingRepository
import app.meetacy.backend.usecase.meetings.participate.ParticipateMeetingUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import org.jetbrains.exposed.sql.Database

fun participateMeetingRepository(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    getMeetingsViewsRepository: GetMeetingsViewsRepository = getMeetingsViewsRepository(db)
): ParticipateMeetingRepository = UsecaseParticipateMeetingRepository(
    usecase = ParticipateMeetingUsecase(
        authRepository = authRepository,
        storage = DatabaseParticipateMeetingStorage(db),
        getMeetingsViewsRepository = getMeetingsViewsRepository
    )
)

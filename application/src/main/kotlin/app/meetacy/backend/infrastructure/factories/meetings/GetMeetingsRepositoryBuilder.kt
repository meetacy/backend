package app.meetacy.backend.infrastructure.factories.meetings

import app.meetacy.backend.endpoint.meetings.get.GetMeetingRepository
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.usecase.integration.meetings.get.UsecaseGetMeetingRepository
import app.meetacy.backend.usecase.meetings.get.GetMeetingUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import org.jetbrains.exposed.sql.Database

fun getMeetingRepository(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    getMeetingsViewsRepository: GetMeetingsViewsRepository = getMeetingsViewsRepository(db)
): GetMeetingRepository = UsecaseGetMeetingRepository(
    usecase = GetMeetingUsecase(
        authRepository = authRepository,
        getMeetingsViewsRepository = getMeetingsViewsRepository
    )
)

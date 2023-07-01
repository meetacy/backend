package app.meetacy.backend.infrastructure.factories.users.get

import app.meetacy.backend.endpoint.users.get.UserRepository
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.usecase.integration.users.get.UsecaseUserRepository
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import app.meetacy.backend.usecase.users.get.GetUserSafeUsecase
import org.jetbrains.exposed.sql.Database

fun getUserRepository(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    getUsersViewsRepository: GetUsersViewsRepository = getUserViewsRepository(db)
): UserRepository = UsecaseUserRepository(
    usecase = GetUserSafeUsecase(
        authRepository = authRepository,
        usersViewsRepository = getUsersViewsRepository
    )
)

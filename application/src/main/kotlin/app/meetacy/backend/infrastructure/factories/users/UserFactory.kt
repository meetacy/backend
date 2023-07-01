package app.meetacy.backend.infrastructure.factories.users

import app.meetacy.backend.endpoint.users.UsersDependencies
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.users.edit.editUserRepository
import app.meetacy.backend.usecase.integration.users.get.UsecaseUserRepository
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import app.meetacy.backend.usecase.users.get.GetUserSafeUsecase
import org.jetbrains.exposed.sql.Database

fun userDependenciesFactory(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    getUsersViewsRepository: GetUsersViewsRepository = getUserViewsRepository(db)
): UsersDependencies = UsersDependencies(
    getUserRepository = UsecaseUserRepository(
        usecase = GetUserSafeUsecase(
            authRepository = authRepository,
            usersViewsRepository = getUsersViewsRepository
        )
    ),
    editUserRepository = editUserRepository(db)
)

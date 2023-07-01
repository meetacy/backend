package app.meetacy.backend.infrastructure.factories

import app.meetacy.backend.database.integration.users.edit.DatabaseEditUserStorage
import app.meetacy.backend.endpoint.users.UsersDependencies
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.files.filesRepository
import app.meetacy.backend.usecase.integration.users.edit.UsecaseEditUserRepository
import app.meetacy.backend.usecase.integration.users.get.UsecaseUserRepository
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.FilesRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import app.meetacy.backend.usecase.users.edit.EditUserUsecase
import app.meetacy.backend.usecase.users.get.GetUserSafeUsecase
import app.meetacy.backend.utf8.integration.DefaultUtf8Checker
import org.jetbrains.exposed.sql.Database

fun userDependenciesFactory(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    filesRepository: FilesRepository = filesRepository(db),
    getUsersViewsRepository: GetUsersViewsRepository
): UsersDependencies = UsersDependencies(
    getUserRepository = UsecaseUserRepository(
        usecase = GetUserSafeUsecase(
            authRepository = authRepository,
            usersViewsRepository = getUsersViewsRepository
        )
    ),
    editUserRepository = UsecaseEditUserRepository(
        usecase = EditUserUsecase(
            storage = DatabaseEditUserStorage(db),
            authRepository = authRepository,
            filesRepository = filesRepository,
            utf8Checker = DefaultUtf8Checker
        )
    )
)

package app.meetacy.backend.database.integration.users.get

import app.meetacy.backend.database.users.UsersStorage
import app.meetacy.backend.feature.auth.database.integration.types.DatabaseFilesRepository
import app.meetacy.backend.feature.auth.database.integration.types.mapToUsecase
import app.meetacy.backend.feature.auth.usecase.types.FullUser
import app.meetacy.backend.feature.auth.usecase.types.UserView
import app.meetacy.backend.feature.auth.usecase.users.get.GetUsersViewsUsecase
import app.meetacy.backend.feature.auth.usecase.users.get.ViewUserUsecase
import app.meetacy.backend.types.user.UserId
import org.jetbrains.exposed.sql.Database

class DatabaseGetUsersViewsStorage(db: Database) : GetUsersViewsUsecase.Storage {
    private val usersStorage = UsersStorage(db)

    override suspend fun getUsers(userIdentities: List<UserId>): List<FullUser?> =
        usersStorage.getUsersOrNull(userIdentities)
            .map { user -> user?.mapToUsecase() }
}

class DatabaseGetUsersViewsViewUserRepository(val db: Database) : GetUsersViewsUsecase.ViewUserRepository {
    override suspend fun viewUser(viewerId: UserId, user: FullUser): UserView =
        ViewUserUsecase(DatabaseFilesRepository(db), DatabaseViewUserStorage(db)).viewUser(viewerId, user)
}

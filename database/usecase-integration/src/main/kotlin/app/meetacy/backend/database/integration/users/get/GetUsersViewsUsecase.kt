package app.meetacy.backend.database.integration.users.get

import app.meetacy.backend.database.integration.types.DatabaseFilesRepository
import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.FullUser
import app.meetacy.backend.usecase.types.UserView
import app.meetacy.backend.usecase.users.get.GetUsersViewsUsecase
import app.meetacy.backend.usecase.users.get.ViewUserUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetUsersViewsStorage(db: Database) : GetUsersViewsUsecase.Storage {
    private val usersTable = UsersTable(db)

    override suspend fun getUsers(userIdentities: List<UserId>): List<FullUser?> =
        usersTable.getUsersOrNull(userIdentities)
            .map { user -> user?.mapToUsecase() }
}

class DatabaseGetUsersViewsViewUserRepository(val db: Database) : GetUsersViewsUsecase.ViewUserRepository {
    override suspend fun viewUser(viewerId: UserId, user: FullUser): UserView =
        ViewUserUsecase(DatabaseFilesRepository(db), DatabaseGetIsSubscriberStorage(db)).viewUser(viewerId, user)
}

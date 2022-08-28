package app.meetacy.backend.database.integration.users

import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.types.FullUser
import app.meetacy.backend.usecase.types.UserView
import app.meetacy.backend.usecase.users.GetUsersViewsUsecase
import app.meetacy.backend.usecase.users.ViewUserUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetUsersViewsStorage(db: Database) : GetUsersViewsUsecase.Storage {
    private val usersTable = UsersTable(db)

    override suspend fun getUsers(userIds: List<UserId>): List<FullUser?> =
        usersTable.getUsers(userIds)
            .map { user -> user.mapToUsecase() }
}

object DatabaseGetUsersViewsViewUserRepository : GetUsersViewsUsecase.ViewUserRepository {
    override suspend fun viewUser(viewerId: UserId, user: FullUser): UserView =
        ViewUserUsecase().viewUser(viewerId, user)
}

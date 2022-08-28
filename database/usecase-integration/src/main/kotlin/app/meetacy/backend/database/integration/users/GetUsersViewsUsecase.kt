package app.meetacy.backend.database.integration.users

import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.types.FullUser
import app.meetacy.backend.usecase.types.UserView
import app.meetacy.backend.usecase.users.GetUsersViewsUsecase
import app.meetacy.backend.usecase.users.ViewUserUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetUsersViewsStorage(private val db: Database) : GetUsersViewsUsecase.Storage {
    override suspend fun getUsers(userIds: List<UserId>): List<FullUser?> =
        userIds
            .map { UsersTable(db)
                .getUser(it)
            }
            .map {  user -> user?.mapToUsecase() }
}

object DatabaseGetUsersViewsViewUserRepository : GetUsersViewsUsecase.ViewUserRepository {
    override suspend fun viewUser(viewerId: UserId, user: FullUser): UserView =
        ViewUserUsecase().viewUser(viewerId, user)
}
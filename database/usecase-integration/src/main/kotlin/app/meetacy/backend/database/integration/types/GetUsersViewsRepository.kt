package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.integration.users.DatabaseGetUsersViewsStorage
import app.meetacy.backend.database.integration.users.DatabaseGetUsersViewsViewUserRepository
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import app.meetacy.backend.usecase.types.UserView
import app.meetacy.backend.usecase.users.GetUsersViewsUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetUsersViewsRepository(private val db: Database) : GetUsersViewsRepository {
    override suspend fun getUsersViewsOrNull(viewerId: UserId, userIds: List<UserId>): List<UserView?> =
        GetUsersViewsUsecase(
            DatabaseGetUsersViewsStorage(db),
            DatabaseGetUsersViewsViewUserRepository
        ).viewUsers(viewerId, userIds)
}
package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.integration.users.get.DatabaseGetUsersViewsStorage
import app.meetacy.backend.database.integration.users.get.DatabaseGetUsersViewsViewUserRepository
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import app.meetacy.backend.usecase.types.UserView
import app.meetacy.backend.usecase.users.get.GetUsersViewsUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetUsersViewsRepository(private val db: Database) : GetUsersViewsRepository {
    override suspend fun getUsersViewsOrNull(viewerId: UserId, userIdentities: List<UserId>): List<UserView?> =
        GetUsersViewsUsecase(
            DatabaseGetUsersViewsStorage(db),
            DatabaseGetUsersViewsViewUserRepository(db)
        ).viewUsers(viewerId, userIdentities)
}

package app.meetacy.backend.feature.users.database.integration.types

import app.meetacy.backend.feature.users.database.integration.users.get.DatabaseGetUsersViewsStorage
import app.meetacy.backend.feature.users.database.integration.users.get.DatabaseGetUsersViewsViewUserRepository
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.UserView
import app.meetacy.backend.feature.users.usecase.get.GetUsersViewsUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetUsersViewsRepository(private val db: Database) : GetUsersViewsRepository {
    override suspend fun getUsersViewsOrNull(viewerId: UserId, userIdentities: List<UserId>): List<UserView?> =
        GetUsersViewsUsecase(
            DatabaseGetUsersViewsStorage(db),
            DatabaseGetUsersViewsViewUserRepository(db)
        ).viewUsers(viewerId, userIdentities)
}

package app.meetacy.backend.mock.integration.types

import app.meetacy.backend.mock.integration.MockGetUsersViewsStorage
import app.meetacy.backend.mock.integration.MockGetUsersViewsViewUserRepository
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import app.meetacy.backend.usecase.types.UserView
import app.meetacy.backend.usecase.users.GetUsersViewsUsecase

object MockGetUsersViewsRepository : GetUsersViewsRepository {
    override suspend fun getUsersViewsOrNull(viewerId: UserId, userIdentities: List<UserId>): List<UserView?> =
        GetUsersViewsUsecase(
            MockGetUsersViewsStorage,
            MockGetUsersViewsViewUserRepository
        ).viewUsers(viewerId, userIdentities)
}

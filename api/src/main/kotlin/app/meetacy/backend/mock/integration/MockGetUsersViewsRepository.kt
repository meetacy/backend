package app.meetacy.backend.mock.integration

import app.meetacy.backend.domain.UserId
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import app.meetacy.backend.usecase.types.UserView

object MockGetUsersViewsRepository : GetUsersViewsRepository {
    override suspend fun getUsersViewsOrNull(viewerId: UserId, userIds: List<UserId>): List<UserView?> =
        mockGetUsersViewsUsecase().viewUsers(viewerId, userIds)
}

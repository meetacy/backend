package app.meetacy.backend.usecase.types

import app.meetacy.backend.domain.UserId

interface GetUsersViewsRepository {
    suspend fun getUsersViewsOrNull(fromUserId: UserId, userIds: List<UserId>): List<UserView?>
}

suspend fun GetUsersViewsRepository.getUsersViews(fromUserId: UserId, userIds: List<UserId>) =
    getUsersViewsOrNull(fromUserId, userIds)
        .filterNotNull()
        .apply {
            require(size == userIds.size) {
                "Cannot find every user ($userIds). If it is a normal case, please consider to use getUsersViewsOrNull"
            }
        }

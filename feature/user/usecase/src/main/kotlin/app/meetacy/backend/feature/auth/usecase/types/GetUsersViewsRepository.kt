package app.meetacy.backend.feature.auth.usecase.types

import app.meetacy.backend.types.user.UserId

interface GetUsersViewsRepository {
    suspend fun getUsersViewsOrNull(viewerId: UserId, userIdentities: List<UserId>): List<UserView?>
}

suspend fun GetUsersViewsRepository.getUsersViews(viewerId: UserId, userIdentities: List<UserId>) =
    getUsersViewsOrNull(viewerId, userIdentities)
        .filterNotNull()
        .apply {
            require(size == userIdentities.size) {
                "Cannot find every user ($userIdentities). If it is a normal case, please consider to use getUsersViewsOrNull"
            }
        }

suspend fun GetUsersViewsRepository.getUserViewOrNull(viewerId: UserId, userId: UserId) =
    getUsersViewsOrNull(viewerId, listOf(userId)).first()

suspend fun GetUsersViewsRepository.getUserView(viewerId: UserId, userId: UserId): UserView =
    getUserViewOrNull(viewerId, userId) ?: error("Cannot find the user with id $userId")

package app.meetacy.backend.types.users

fun interface GetUsersViewsRepository {
    suspend fun getUsersViewsOrNull(viewerId: UserId, userIdentities: List<UserId>): List<UserView?>
}

suspend fun GetUsersViewsRepository.getUsersViews(viewerId: UserId, userIds: List<UserId>) =
    getUsersViewsOrNull(viewerId, userIds)
        .filterNotNull()
        .apply {
            require(size == userIds.size) {
                "Cannot find every user ($userIds). If it is a normal case, please consider to use getUsersViewsOrNull"
            }
        }

suspend fun GetUsersViewsRepository.getUserViewOrNull(viewerId: UserId, userId: UserId) =
    getUsersViewsOrNull(viewerId, listOf(userId)).first()

suspend fun GetUsersViewsRepository.getUserView(viewerId: UserId, userId: UserId): UserView =
    getUserViewOrNull(viewerId, userId) ?: error("Cannot find the user with id $userId")

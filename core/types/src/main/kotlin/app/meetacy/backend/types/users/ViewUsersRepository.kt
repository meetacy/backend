package app.meetacy.backend.types.users

fun interface ViewUsersRepository {
    suspend fun viewUsers(viewerId: UserId, users: List<FullUser>): List<UserView>
}

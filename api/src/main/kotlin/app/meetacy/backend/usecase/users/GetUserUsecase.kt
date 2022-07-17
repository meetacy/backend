package app.meetacy.backend.usecase.users

class GetUserUsecase(private val storage: Storage) {
    fun getUser(params: Request): Result {
        val ownerId = storage.getTokenOwnerId(params.accessToken)
            ?: return Result.InvalidToken

        val userId = when (params) {
            is Request.Self -> ownerId
            is Request.User -> params.id
        }

        val user = storage.getUser(userId)
            ?: return Result.UserNotFound

        if (params is Request.User && params.accessHash != user.accessHash)
            return Result.UserNotFound

        val result = if (ownerId == userId) {
            User(
                id = user.id,
                accessHash = user.accessHash,
                nickname = user.nickname,
                email = user.email,
                emailVerified = user.emailVerified
            )
        } else {
            User(
                id = user.id,
                accessHash = user.accessHash,
                nickname = user.nickname,
                email = null,
                emailVerified = null
            )
        }

        return Result.Success(result)
    }

    interface Storage {
        fun getTokenOwnerId(token: String): Long?
        fun getUser(id: Long): User?
    }

    class User(
        val id: Long,
        val accessHash: String,
        val nickname: String,
        val email: String?,
        val emailVerified: Boolean?
    )

    sealed interface Request {
        val accessToken: String
        class Self(override val accessToken: String) : Request
        class User(val id: Long, val accessHash: String, override val accessToken: String) : Request
    }

    sealed interface Result {
        object InvalidToken : Result
        object UserNotFound : Result
        class Success(val user: User) : Result
    }
}

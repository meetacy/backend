package app.meetacy.backend.usecase.integration.users

import app.meetacy.backend.endpoint.users.GetUserResult
import app.meetacy.backend.endpoint.users.UserRepository
import app.meetacy.backend.endpoint.users.UserResponse
import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.users.GetUserSafeUsecase

class UsecaseUserRepository(private val usecase: GetUserSafeUsecase) : UserRepository {

    override suspend fun getUser(id: UserId?, accessHash: AccessHash?, accessToken: AccessToken): GetUserResult {
        val params = if (accessHash == null && id == null)
            GetUserSafeUsecase.Params.Self(
                accessToken = accessToken
            )
        else if (accessHash != null && id != null)
            GetUserSafeUsecase.Params.User(
                id = id,
                accessHash = accessHash,
                accessToken = accessToken
            )
        else return GetUserResult.UserNotFound

        return when (val result = usecase.getUser(params)) {
            is GetUserSafeUsecase.Result.Success -> GetUserResult.Success(
                UserResponse(
                    id = result.user.id,
                    accessHash = result.user.accessHash,
                    nickname = result.user.nickname,
                    email = result.user.email,
                    emailVerified = result.user.emailVerified
                )
            )
            is GetUserSafeUsecase.Result.InvalidToken -> GetUserResult.InvalidToken
            is GetUserSafeUsecase.Result.UserNotFound -> GetUserResult.UserNotFound
        }
    }
}

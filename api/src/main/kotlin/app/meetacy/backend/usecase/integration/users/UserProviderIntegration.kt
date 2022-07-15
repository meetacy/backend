package app.meetacy.backend.usecase.integration.users

import app.meetacy.backend.endpoint.users.GetUserParams
import app.meetacy.backend.endpoint.users.GetUserResult
import app.meetacy.backend.endpoint.users.UserProvider
import app.meetacy.backend.endpoint.users.UserResponse
import app.meetacy.backend.usecase.users.GetUserUsecase

private class UserProviderIntegration(private val usecase: GetUserUsecase) : UserProvider {
    override fun getUser(getUserParams: GetUserParams): GetUserResult {
        val request = if (getUserParams.accessHash == null && getUserParams.id == null)
            GetUserUsecase.Request.Self(
                accessToken = getUserParams.accessToken
            )
        else if (getUserParams.accessHash != null && getUserParams.id != null)
            GetUserUsecase.Request.User(
                id = getUserParams.id,
                accessHash = getUserParams.accessHash,
                accessToken = getUserParams.accessToken
            )
        else return GetUserResult.UserNotFound

        return when (val result = usecase.getUser(request)) {
            is GetUserUsecase.Result.Success -> GetUserResult.Success(
                UserResponse(
                    id = result.user.id,
                    accessHash = result.user.accessHash,
                    nickname = result.user.nickname,
                    email = result.user.email,
                    emailVerified = result.user.emailVerified
                )
            )
            is GetUserUsecase.Result.InvalidToken -> GetUserResult.InvalidToken
            is GetUserUsecase.Result.UserNotFound -> GetUserResult.UserNotFound
        }
    }
}

fun usecaseUserProvider(usecase: GetUserUsecase): UserProvider = UserProviderIntegration(usecase)

package app.meetacy.backend.usecase.integration.users

import app.meetacy.backend.endpoint.users.GetUserParams
import app.meetacy.backend.endpoint.users.GetUserResult
import app.meetacy.backend.endpoint.users.UserProvider
import app.meetacy.backend.endpoint.users.UserResponse
import app.meetacy.backend.usecase.users.GetUserSafeUsecase

private class UserProviderIntegration(private val usecase: GetUserSafeUsecase) : UserProvider {
    override fun getUser(getUserParams: GetUserParams): GetUserResult {
        val params = if (getUserParams.accessHash == null && getUserParams.id == null)
            GetUserSafeUsecase.Params.Self(
                accessToken = getUserParams.accessToken
            )
        else if (getUserParams.accessHash != null && getUserParams.id != null)
            GetUserSafeUsecase.Params.User(
                id = getUserParams.id,
                accessHash = getUserParams.accessHash,
                accessToken = getUserParams.accessToken
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

fun usecaseUserProvider(usecase: GetUserSafeUsecase): UserProvider = UserProviderIntegration(usecase)

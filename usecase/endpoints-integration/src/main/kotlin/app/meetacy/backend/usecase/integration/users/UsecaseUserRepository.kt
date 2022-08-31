package app.meetacy.backend.usecase.integration.users

import app.meetacy.backend.endpoint.users.GetUserResult
import app.meetacy.backend.endpoint.users.UserRepository
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserIdentity
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.users.GetUserSafeUsecase

class UsecaseUserRepository(private val usecase: GetUserSafeUsecase) : UserRepository {

    override suspend fun getUser(identity: UserIdentity?, accessToken: AccessToken): GetUserResult {
        val params = if (identity?.accessHash == null && identity?.userId == null)
            GetUserSafeUsecase.Params.Self(accessToken)
        else GetUserSafeUsecase.Params.User(
                identity = identity,
                accessToken = accessToken
        )

        return when (val result = usecase.getUser(params)) {
            is GetUserSafeUsecase.Result.Success -> GetUserResult.Success(result.user.mapToEndpoint())
            is GetUserSafeUsecase.Result.InvalidToken -> GetUserResult.InvalidToken
            is GetUserSafeUsecase.Result.UserNotFound -> GetUserResult.UserNotFound
        }
    }
}

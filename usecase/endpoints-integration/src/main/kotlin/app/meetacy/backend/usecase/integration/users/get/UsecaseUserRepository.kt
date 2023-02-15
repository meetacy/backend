package app.meetacy.backend.usecase.integration.users.get

import app.meetacy.backend.endpoint.users.get.GetUserResult
import app.meetacy.backend.endpoint.users.get.UserRepository
import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.UserIdentity
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.users.get.GetUserSafeUsecase

class UsecaseUserRepository(private val usecase: GetUserSafeUsecase) : UserRepository {

    override suspend fun getUser(identity: UserIdentity?, accessIdentity: AccessIdentity): GetUserResult {
        val params = if (identity?.accessHash == null && identity?.userId == null)
            GetUserSafeUsecase.Params.Self(accessIdentity)
        else GetUserSafeUsecase.Params.User(
            identity = identity,
            accessIdentity = accessIdentity
        )

        return when (val result = usecase.getUser(params)) {
            is GetUserSafeUsecase.Result.Success -> GetUserResult.Success(result.user.mapToEndpoint())
            is GetUserSafeUsecase.Result.InvalidToken -> GetUserResult.InvalidIdentity
            is GetUserSafeUsecase.Result.UserNotFound -> GetUserResult.UserNotFound
        }
    }
}
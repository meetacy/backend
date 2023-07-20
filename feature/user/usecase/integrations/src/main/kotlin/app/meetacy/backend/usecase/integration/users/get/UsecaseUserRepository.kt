package app.meetacy.backend.usecase.integration.users.get

import app.meetacy.backend.endpoint.users.get.GetUserParams
import app.meetacy.backend.endpoint.users.get.GetUserResult
import app.meetacy.backend.endpoint.users.get.UserRepository
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.users.get.GetUserSafeUsecase

class UsecaseUserRepository(private val usecase: GetUserSafeUsecase) : UserRepository {

    override suspend fun getUser(params: GetUserParams): GetUserResult = with(params) {
        val identity = id?.type()
        val accessIdentity = token.type()

        val usecaseParams = if (identity?.accessHash == null && identity?.id == null)
            GetUserSafeUsecase.Params.Self(accessIdentity)
        else GetUserSafeUsecase.Params.User(
            identity = identity,
            accessIdentity = accessIdentity
        )

        return when (val result = usecase.getUser(usecaseParams)) {
            is GetUserSafeUsecase.Result.Success -> GetUserResult.Success(result.user.mapToEndpoint())
            is GetUserSafeUsecase.Result.InvalidToken -> GetUserResult.InvalidIdentity
            is GetUserSafeUsecase.Result.UserNotFound -> GetUserResult.UserNotFound
        }
    }
}
package app.meetacy.backend.feature.users.usecase.integration.users.get

import app.meetacy.backend.feature.users.endpoints.get.GetUserParams
import app.meetacy.backend.feature.users.endpoints.get.GetUserResult
import app.meetacy.backend.feature.users.endpoints.get.UserRepository
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.users.type
import app.meetacy.backend.feature.users.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.feature.users.usecase.get.GetUserSafeUsecase

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
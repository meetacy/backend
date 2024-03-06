package app.meetacy.backend.feature.users.endpoints.integration.get

import app.meetacy.backend.feature.users.endpoints.get.GetUserResult
import app.meetacy.backend.feature.users.endpoints.get.UserRepository
import app.meetacy.backend.feature.users.endpoints.get.getUser
import app.meetacy.backend.feature.users.usecase.get.GetUserSafeUsecase
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.users.serializable
import app.meetacy.backend.types.serializable.users.type
import app.meetacy.di.DI
import io.ktor.server.routing.*

internal fun Route.getUser(di: DI) {
    val usecase: GetUserSafeUsecase by di.getting
    val repository = object : UserRepository {
        override suspend fun getUser(
            identifier: UserRepository.Identifier
        ): GetUserResult {
            return when (
                val result = usecase.getUser(
                    when (identifier) {
                        is UserRepository.Identifier.ByUserId -> GetUserSafeUsecase.Identifier.ByUserId(
                            identity = identifier.userId.type(),
                            accessIdentity = identifier.accessIdentity.type()
                        )
                        is UserRepository.Identifier.ByUsername -> GetUserSafeUsecase.Identifier.ByUsername(
                            username = identifier.username.type(),
                            accessIdentity = identifier.accessIdentity.type()
                        )
                        is UserRepository.Identifier.Self -> GetUserSafeUsecase.Identifier.Self(
                            accessIdentity = identifier.accessIdentity.type()
                        )
                    }
                )
            ) {
                GetUserSafeUsecase.Result.InvalidToken -> GetUserResult.InvalidIdentity
                GetUserSafeUsecase.Result.UserNotFound -> GetUserResult.UserNotFound
                is GetUserSafeUsecase.Result.Success -> GetUserResult.Success(result.user.serializable())
            }
        }
    }

    getUser(repository)
}

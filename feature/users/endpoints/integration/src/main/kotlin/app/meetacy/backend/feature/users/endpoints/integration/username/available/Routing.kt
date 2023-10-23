package app.meetacy.backend.feature.users.endpoints.integration.username.available

import app.meetacy.backend.feature.users.endpoints.username.available.UsernameAvailableRepository
import app.meetacy.backend.feature.users.endpoints.username.available.UsernameAvailableResult
import app.meetacy.backend.feature.users.endpoints.username.available.usernameAvailable
import app.meetacy.backend.feature.users.usecase.username.available.UsernameAvailableUsecase
import app.meetacy.backend.types.serializable.users.serializable
import app.meetacy.di.DI
import io.ktor.server.routing.*

internal fun Route.usernameAvailable(di: DI) {
    val repository = object : UsernameAvailableRepository {
        val usecase: UsernameAvailableUsecase by di.getting
        override suspend fun usernameAvailable(username: String): UsernameAvailableResult = when (val result = usecase.usernameAvailable(username)) {
            UsernameAvailableUsecase.Result.InvalidUtf8String -> UsernameAvailableResult.InvalidUsernameString
            UsernameAvailableUsecase.Result.UsernameAlreadyOccupied -> UsernameAvailableResult.AlreadyOccupiedUsername
            is UsernameAvailableUsecase.Result.Success -> UsernameAvailableResult.Success(result.username.serializable())
        }
    }
    usernameAvailable(repository)
}

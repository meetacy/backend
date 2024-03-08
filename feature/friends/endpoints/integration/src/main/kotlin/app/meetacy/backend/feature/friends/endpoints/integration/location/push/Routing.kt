package app.meetacy.backend.feature.friends.endpoints.integration.location.push

import app.meetacy.backend.feature.friends.endpoints.location.push.PushLocationRepository
import app.meetacy.backend.feature.friends.endpoints.location.push.pushLocation
import app.meetacy.backend.feature.friends.usecase.location.push.PushLocationUsecase
import app.meetacy.backend.feature.friends.usecase.location.push.PushLocationUsecase.Result
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.location.Location
import app.meetacy.backend.types.serializable.location.type
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.pushLocation(di: DI) {
    val usecase: PushLocationUsecase by di.getting

    val repository = object : PushLocationRepository {
        override suspend fun push(
            accessIdentity: AccessIdentity,
            location: Location
        ): PushLocationRepository.Result = when (
            usecase.push(
                accessIdentity = accessIdentity.type(),
                location = location.type()
            )
        ) {
            Result.Success -> PushLocationRepository.Result.Success
            Result.TokenInvalid -> PushLocationRepository.Result.TokenInvalid
        }
    }

    pushLocation(repository)
}

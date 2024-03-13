package app.meetacy.backend.feature.friends.usecase.integration.location.push

import app.meetacy.backend.feature.friends.usecase.location.LocationsMiddleware
import app.meetacy.backend.feature.friends.usecase.location.push.PushLocationUsecase
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.pushLocationUsecase() {
    val pushLocationUsecase by singleton {
        val locationsMiddleware: LocationsMiddleware by getting

        val storage = object : PushLocationUsecase.Storage {
            override suspend fun setLocation(userId: UserId, location: Location) {
                locationsMiddleware.setLocation(userId, location)
            }
        }

        val authRepository: AuthRepository by getting

        PushLocationUsecase(storage, authRepository)
    }
}

package app.meetacy.backend.usecase.integration.types

import app.meetacy.backend.endpoint.types.User
import app.meetacy.backend.types.serialization.datetime.serializable
import app.meetacy.backend.types.serialization.file.serializable
import app.meetacy.backend.types.serialization.location.serializable
import app.meetacy.backend.types.serialization.user.serializable
import app.meetacy.backend.usecase.types.UserView
import app.meetacy.backend.endpoint.types.UserOnMap as EndpointUserOnMap
import app.meetacy.backend.usecase.types.UserOnMap as UsecaseUserOnMap

fun UserView.mapToEndpoint() = User(
    isSelf = isSelf,
    id = identity.serializable(),
    nickname = nickname,
    email = email,
    emailVerified = emailVerified,
    avatarIdentity = avatarIdentity?.serializable()
)

fun UsecaseUserOnMap.mapToEndpoint() = EndpointUserOnMap(
    user = user.mapToEndpoint(),
    location = location.serializable(),
    capturedAt = capturedAt.serializable()
)

fun User.mapToUsecase(): UserView = UserView(
    isSelf = isSelf,
    identity = id.type(),
    nickname = nickname,
    email = email,
    emailVerified = emailVerified,
    avatarIdentity = avatarIdentity?.type()
)

fun EndpointUserOnMap.mapToUsecase() = UsecaseUserOnMap(
    user = user.mapToUsecase(),
    location = location.type(),
    capturedAt = capturedAt.type()
)

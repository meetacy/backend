package app.meetacy.backend.usecase.integration.types

import app.meetacy.backend.endpoint.types.user.User
import app.meetacy.backend.types.serialization.datetime.serializable
import app.meetacy.backend.types.serialization.file.serializable
import app.meetacy.backend.types.serialization.location.serializable
import app.meetacy.backend.types.serialization.user.serializable
import app.meetacy.backend.usecase.types.UserView
import app.meetacy.backend.endpoint.types.user.UserLocationSnapshot as EndpointUserLocationSnapshot
import app.meetacy.backend.usecase.types.UserLocationSnapshot as UsecaseUserLocationSnapshot

fun UserView.mapToEndpoint() = User(
    isSelf = isSelf,
    relationship = relationship?.serializable(),
    id = identity.serializable(),
    nickname = nickname,
    email = email,
    emailVerified = emailVerified,
    username = username?.serializable(),
    avatarId = avatarIdentity?.serializable()
)

fun UsecaseUserLocationSnapshot.mapToEndpoint() = EndpointUserLocationSnapshot(
    user = user.mapToEndpoint(),
    location = location.serializable(),
    capturedAt = capturedAt.serializable()
)

fun User.mapToUsecase(): UserView = UserView(
    isSelf = isSelf,
    relationship = relationship?.type(),
    identity = id.type(),
    nickname = nickname,
    email = email,
    emailVerified = emailVerified,
    username = username?.type(),
    avatarIdentity = avatarId?.type()
)

fun EndpointUserLocationSnapshot.mapToUsecase() = UsecaseUserLocationSnapshot(
    user = user.mapToUsecase(),
    location = location.type(),
    capturedAt = capturedAt.type()
)

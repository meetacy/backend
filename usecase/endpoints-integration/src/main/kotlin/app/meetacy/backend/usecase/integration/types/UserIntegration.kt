package app.meetacy.backend.usecase.integration.types

import app.meetacy.backend.endpoint.types.User
import app.meetacy.backend.types.serialization.serializable
import app.meetacy.backend.usecase.types.UserView

fun UserView.mapToEndpoint() = User(
    identity = identity.serializable(),
    nickname = nickname,
    email = email,
    emailVerified = emailVerified,
    avatarId = avatarIdentity?.serializable()
)

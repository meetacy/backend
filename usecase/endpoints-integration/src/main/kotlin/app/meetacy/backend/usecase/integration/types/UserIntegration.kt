package app.meetacy.backend.usecase.integration.types

import app.meetacy.backend.endpoint.types.User
import app.meetacy.backend.types.serialization.file.serializable
import app.meetacy.backend.types.serialization.gender.serializable
import app.meetacy.backend.types.serialization.user.serializable
import app.meetacy.backend.usecase.types.UserView

fun UserView.mapToEndpoint() = User(
    isSelf = isSelf,
    id = identity.serializable(),
    gender = gender?.serializable(),
    nickname = nickname,
    email = email,
    emailVerified = emailVerified,
    avatarIdentity = avatarIdentity?.serializable()
)

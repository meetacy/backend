package app.meetacy.backend.usecase.integration.types

import app.meetacy.backend.endpoint.types.User
import app.meetacy.backend.usecase.types.UserView

fun UserView.mapToDomain() = User(
    id = id,
    accessHash = accessHash,
    nickname = nickname,
    email = email,
    emailVerified = emailVerified
)

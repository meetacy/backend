package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.types.DatabaseUser
import app.meetacy.backend.types.users.FullUser

fun DatabaseUser.mapToUsecase() = FullUser(identity, nickname, username, email, emailVerified, avatarId)

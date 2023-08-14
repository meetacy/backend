package app.meetacy.backend.feature.users.database.integration.types

import app.meetacy.backend.feature.users.database.types.DatabaseUser
import app.meetacy.backend.types.users.FullUser

fun DatabaseUser.mapToUsecase() = FullUser(identity, nickname, username, email, emailVerified, avatarId)

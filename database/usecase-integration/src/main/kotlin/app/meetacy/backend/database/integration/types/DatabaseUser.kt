package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.types.DatabaseUser
import app.meetacy.backend.usecase.types.FullUser

fun DatabaseUser.mapToUsecase() = FullUser(identity, gender, nickname, email, emailVerified, avatarId)

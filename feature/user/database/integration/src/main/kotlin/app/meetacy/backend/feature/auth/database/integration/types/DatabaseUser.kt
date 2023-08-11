package app.meetacy.backend.feature.auth.database.integration.types

import app.meetacy.backend.database.types.DatabaseUser
import app.meetacy.backend.feature.auth.usecase.types.FullUser

fun DatabaseUser.mapToUsecase() = FullUser(identity, nickname, username, email, emailVerified, avatarId)

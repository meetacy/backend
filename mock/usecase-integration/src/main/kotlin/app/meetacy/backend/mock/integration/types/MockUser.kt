package app.meetacy.backend.mock.integration.types

import app.meetacy.backend.mock.storage.MockUser
import app.meetacy.backend.usecase.types.FullUser

fun MockUser.mapToUsecase() = FullUser(id, accessHash, nickname, email, emailVerified)

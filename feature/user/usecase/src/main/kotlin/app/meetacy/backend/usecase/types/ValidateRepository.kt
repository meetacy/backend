package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.user.Username

interface ValidateRepository {
    suspend fun isOccupied(username: Username): Boolean
}

suspend inline fun ValidateRepository.isOccupied(
    username: Username,
    fallback: () -> Nothing
) {
    if(isOccupied(username)) fallback()
}

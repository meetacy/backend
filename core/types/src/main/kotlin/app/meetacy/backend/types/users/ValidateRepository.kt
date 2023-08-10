package app.meetacy.backend.types.users

interface ValidateRepository {
    suspend fun isOccupied(username: Username): Boolean
}

suspend inline fun ValidateRepository.isOccupied(
    username: Username,
    fallback: () -> Nothing
) {
    if(isOccupied(username)) fallback()
}

package app.meetacy.backend.types.auth

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.user.UserId

interface AuthRepository {
    suspend fun authorize(accessIdentity: AccessIdentity): Boolean
}

suspend inline fun AuthRepository.authorize(
    accessIdentity: AccessIdentity,
    fallback: () -> Nothing
) {
    if(!authorize(accessIdentity)) fallback()
}

suspend inline fun AuthRepository.authorizeWithUserId(
    accessIdentity: AccessIdentity,
    fallback: () -> Nothing
): UserId {
    authorize(accessIdentity, fallback)
    return accessIdentity.userId
}

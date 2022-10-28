package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.FileId
import app.meetacy.backend.types.FileIdentity

interface FilesRepository {
    suspend fun authorize(fileIdentity: FileIdentity): Boolean
}

suspend inline fun FilesRepository.authorize(
    fileIdentity: FileIdentity,
    fallback: () -> Nothing
) {
    if(!authorize(fileIdentity)) fallback()
}

suspend inline fun FilesRepository.authorizeWithFileId(
    fileIdentity: FileIdentity,
    fallback: () -> Nothing
): FileId {
    authorize(fileIdentity, fallback)
    return fileIdentity.fileId
}
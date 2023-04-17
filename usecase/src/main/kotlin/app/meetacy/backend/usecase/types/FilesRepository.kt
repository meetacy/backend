package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity

interface FilesRepository {
    suspend fun getFileIdentities(fileIdList: List<FileId>): List<FileIdentity?>
}

suspend fun FilesRepository.getFileIdentity(fileId: FileId): FileIdentity? =
    getFileIdentities(listOf(fileId)).first()

suspend fun FilesRepository.checkFileIdentity(identity: FileIdentity): Boolean {
    return getFileIdentity(identity.id)?.accessHash == identity.accessHash
}

suspend inline fun FilesRepository.checkFile(
    fileIdentity: FileIdentity,
    fallback: () -> Nothing
) {
    if(!checkFileIdentity(fileIdentity)) fallback()
}

suspend inline fun FilesRepository.checkFileIdentity(
    fileIdentity: FileIdentity,
    fallback: () -> Nothing
): FileIdentity = if (checkFileIdentity(fileIdentity)) {
    fileIdentity
} else {
    fallback()
}

suspend inline fun FilesRepository.getFileIdentity(
    fileId: FileId,
    fallback: () -> Nothing
): FileIdentity {
    val fileIdentity = getFileIdentity(fileId)
    if (fileIdentity == null) {
        fallback()
    } else return fileIdentity
}

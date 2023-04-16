package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity

interface FilesRepository {
    suspend fun checkFile(identity: FileIdentity): Boolean
    suspend fun getFileIdentity(fileId: FileId, fileAccessIdentity: FileIdentity? = null): FileIdentity?
    suspend fun getFileIdentityList(fileIdList: List<FileId?>): List<FileIdentity?>
}

suspend inline fun FilesRepository.checkFile(
    fileIdentity: FileIdentity,
    fallback: () -> Nothing
) {
    if(!checkFile(fileIdentity)) fallback()
}

suspend inline fun FilesRepository.getFileIdentity(
    fileId: FileId,
    fileAccessIdentity: FileIdentity? = null,
    fallback: () -> Nothing
): FileIdentity {
    val fileIdentity = getFileIdentity(fileId, fileAccessIdentity)
    if (fileIdentity == null) {
        fallback()
    } else return fileIdentity
}

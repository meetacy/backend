package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.FileIdentity

interface FilesRepository {
    suspend fun checkFile(identity: FileIdentity): Boolean
}

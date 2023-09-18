package app.meetacy.backend.types.integration.files

import app.meetacy.backend.feature.files.database.FilesStorage
import app.meetacy.backend.types.files.FileId
import app.meetacy.backend.types.files.FilesRepository
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.filesRepository() {
    val filesRepository by singleton<FilesRepository> {
        val filesStorage: FilesStorage by getting

        object : FilesRepository {
            override suspend fun getFileIdentities(
                fileIdList: List<FileId>
            ) = filesStorage.getFileIdentityList(fileIdList)
        }
    }
}

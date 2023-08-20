package app.meetacy.backend.feature.files.usecase.integration.get

import app.meetacy.backend.feature.files.database.FilesStorage
import app.meetacy.backend.feature.files.usecase.get.GetFileUsecase
import app.meetacy.backend.types.files.FileId
import app.meetacy.di.builder.DIBuilder
import java.io.File

fun DIBuilder.getFileUsecase() {
    val getFileUsecase by singleton {
        val filesBasePath: String by getting
        val filesStorage: FilesStorage by getting

        val storage = object : GetFileUsecase.Storage {
            override suspend fun getFileDescription(
                id: FileId
            ) = filesStorage.getFileDescription(id)
        }

        GetFileUsecase(
            filesDirectory = File(filesBasePath),
            storage = storage
        )
    }
}

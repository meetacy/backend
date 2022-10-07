package app.meetacy.backend.endpoint.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object JvmFileUploader {
    suspend fun upload(
        inputStream: InputStream,
        file: File
    ) {
        withContext(Dispatchers.IO) {
            file.createNewFile()
            inputStream.transferTo(FileOutputStream(file))
        }
    }
}

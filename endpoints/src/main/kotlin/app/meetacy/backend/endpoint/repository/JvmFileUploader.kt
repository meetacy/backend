package app.meetacy.backend.endpoint.repository

import app.meetacy.backend.types.file.FileSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*
import java.util.*


object JvmFileUploader {
    suspend fun upload(
        inputStream: InputStream,
        file: File,
        limit: FileSize
    ): FileSize? = withContext(Dispatchers.IO) {
        try {
            file.createNewFile()
            val stream = FileOutputStream(file)
            val writtenSize = inputStream.transferTo(stream, limit)
            stream.close()
            if (writtenSize != file.length()) {
                file.delete()
                return@withContext null
            }
            return@withContext FileSize(writtenSize)
        } catch (e: Throwable) {
            e.printStackTrace()
            null
        }
    }
}

@Throws(IOException::class)
fun InputStream.transferTo(out: OutputStream, limit: FileSize): Long {
    var transferred: Long = 0
    val buffer = ByteArray(8192)
    var read: Int

    while (read(buffer).also { read = it } >= 0) {
        out.write(buffer, 0, read)
        if (transferred <= limit.bytesSize) {
            transferred += read.toLong()
        } else {
            return transferred
        }
    }

    return transferred
}

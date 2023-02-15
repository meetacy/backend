package app.meetacy.backend.endpoint.repository

import app.meetacy.backend.types.FileSize
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
            val bytesSize = inputStream.transferTo(stream, limit)
            stream.close()
            if (bytesSize == null) {
                file.delete()
                return@withContext null
            }
            return@withContext FileSize(bytesSize)
        } catch (e: Throwable) {
            e.printStackTrace()
            null
        }
    }
}

@Throws(IOException::class)
fun InputStream.transferTo(out: OutputStream, limit: FileSize): Long? {
    Objects.requireNonNull(out, "out")
    var transferred: Long = 0
    val buffer = ByteArray(8192)
    var read: Int
    while (this.read(buffer, 0, 8192).also { read = it } >= 0) {
        out.write(buffer, 0, read)
        if (transferred <= limit.bytesSize) {
            transferred += read.toLong()
        } else return null
    }
    return transferred
}

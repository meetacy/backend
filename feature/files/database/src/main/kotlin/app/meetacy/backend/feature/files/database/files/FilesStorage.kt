@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.feature.files.database.files

import app.meetacy.backend.constants.FILE_NAME_MAX_LIMIT
import app.meetacy.backend.constants.HASH_LENGTH
import app.meetacy.backend.feature.files.database.files.FilesTable.ACCESS_HASH
import app.meetacy.backend.feature.files.database.files.FilesTable.FILE_ID
import app.meetacy.backend.feature.files.database.files.FilesTable.FILE_SIZE
import app.meetacy.backend.feature.files.database.files.FilesTable.ORIGINAL_FILE_NAME
import app.meetacy.backend.feature.files.database.files.FilesTable.USER_ID
import app.meetacy.backend.feature.files.database.types.DatabaseFileDescription
import app.meetacy.backend.feature.users.database.users.UsersTable
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.file.FileSize
import app.meetacy.backend.types.users.UserId
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object FilesTable : Table() {
    val FILE_ID = long("FILE_ID").autoIncrement()
    val USER_ID = reference("USER_ID", UsersTable.USER_ID)
    val ACCESS_HASH = varchar("ACCESS_HASH", HASH_LENGTH)
    val FILE_SIZE = long("FILE_SIZE").nullable()
    val ORIGINAL_FILE_NAME = varchar("ORIGINAL_FILE_NAME", FILE_NAME_MAX_LIMIT)

    override val primaryKey: PrimaryKey = PrimaryKey(FILE_ID)
}

class FilesStorage(private val db: Database) {

    suspend fun saveFileDescription(userId: UserId, accessHash: AccessHash, fileName: String): FileIdentity =
        newSuspendedTransaction(Dispatchers.IO, db) {
            val result = FilesTable.insert { statement ->
                statement[USER_ID] = userId.long
                statement[ACCESS_HASH] = accessHash.string
                statement[ORIGINAL_FILE_NAME] = fileName
            }
            return@newSuspendedTransaction FileIdentity(
                FileId(result[FILE_ID]),
                accessHash
            )
        }

    suspend fun updateFileSize(fileId: FileId, fileSize: FileSize) =
        newSuspendedTransaction(Dispatchers.IO, db) {
            FilesTable.update({ FILE_ID eq fileId.long }) { statement ->
                statement[FILE_SIZE] = fileSize.bytesSize
            }
        }

    suspend fun getUserFullSize(userId: UserId): FileSize =
        newSuspendedTransaction(Dispatchers.IO, db) {
            val fileSize = FilesTable.select { (USER_ID eq userId.long) }.sumOf { it[FILE_SIZE] ?: 0 }
            return@newSuspendedTransaction FileSize(fileSize)
        }

    suspend fun getFileDescription(fileId: FileId): DatabaseFileDescription? =
        newSuspendedTransaction(Dispatchers.IO, db) {
            val result = FilesTable.select { (FILE_ID eq fileId.long) }
                .firstOrNull() ?: return@newSuspendedTransaction null
            val userId = result[USER_ID]
            val fileName = result[ORIGINAL_FILE_NAME]
            val fileSize = result[FILE_SIZE]?.let { fileSize -> FileSize(fileSize) }
            val fileIdentity = FileIdentity(
                FileId(result[FILE_ID]),
                AccessHash(result[ACCESS_HASH])
            )
            return@newSuspendedTransaction DatabaseFileDescription(UserId(userId), fileSize, fileIdentity, fileName)
        }

    suspend fun getFileIdentity(fileId: FileId): FileIdentity? =
        newSuspendedTransaction(Dispatchers.IO, db) {
            val result = FilesTable.select { (FILE_ID eq fileId.long) }
                    .firstOrNull() ?: return@newSuspendedTransaction null
            return@newSuspendedTransaction FileIdentity(
                FileId(result[FILE_ID]),
                AccessHash(result[ACCESS_HASH])
            )
        }

    suspend fun getFileIdentityList(fileIdList: List<FileId?>): List<FileIdentity?> =
        newSuspendedTransaction(Dispatchers.IO, db) {
            val rawFileIds = fileIdList.map { it?.long }

            val fileIdentityList = mutableListOf<FileIdentity?>()

            for (fileId in rawFileIds) {
                if (fileId == null) {
                    fileIdentityList.add(fileId)
                } else {
                    val result = FilesTable.select { FILE_ID eq fileId }
                        .first()
                    fileIdentityList.add(FileIdentity(FileId(fileId), AccessHash(result[ACCESS_HASH])))
                }
            }

            return@newSuspendedTransaction fileIdentityList
        }
}

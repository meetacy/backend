@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.files

import app.meetacy.backend.database.types.DatabaseFileDescription
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.file.FileSize
import app.meetacy.backend.types.user.UserId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class FilesTable(private val db: Database) : Table() {
    private val USER_ID = long("USER_ID")
    private val FILE_ID = long("FILE_ID").autoIncrement()
    private val ACCESS_HASH = varchar("ACCESS_HASH", 256)
    private val FILE_SIZE = long("FILE_SIZE").nullable()
    private val ORIGINAL_FILE_NAME = varchar("ORIGINAL_FILE_NAME", 1024)

    init {
        transaction(db) {
            SchemaUtils.create(this@FilesTable)
        }
    }

    suspend fun saveFileDescription(userId: UserId, accessHash: AccessHash, fileName: String): FileIdentity =
        newSuspendedTransaction(db = db) {
            val result = insert { statement ->
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
        newSuspendedTransaction(db = db) {
            update({ FILE_ID eq fileId.long }) { statement ->
                statement[FILE_SIZE] = fileSize.bytesSize
            }
        }

    suspend fun getUserFullSize(userId: UserId): FileSize =
        newSuspendedTransaction(db = db) {
            return@newSuspendedTransaction FileSize(select { (USER_ID eq userId.long) }.sumOf { it[FILE_SIZE] ?: 0 })
        }

    suspend fun getFileDescription(fileId: FileId): DatabaseFileDescription? =
        newSuspendedTransaction(db = db) {
            val result = select { (FILE_ID eq fileId.long) }
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
        newSuspendedTransaction(db = db) {
            val result = select { (FILE_ID eq fileId.long) }
                    .firstOrNull() ?: return@newSuspendedTransaction null
            return@newSuspendedTransaction FileIdentity(
                FileId(result[FILE_ID]),
                AccessHash(result[ACCESS_HASH])
            )
        }

    suspend fun getFileIdentityList(fileIdList: List<FileId?>): List<FileIdentity?> =
        newSuspendedTransaction(db = db) {
            val rawFileIds = fileIdList.map { it?.long }

            val fileIdentityList = mutableListOf<FileIdentity?>()

            for (fileId in rawFileIds) {
                if (fileId == null) {
                    fileIdentityList.add(fileId)
                } else {
                    val result = select { FILE_ID eq fileId }
                        .first()
                    fileIdentityList.add(FileIdentity(FileId(fileId), AccessHash(result[ACCESS_HASH])))
                }
            }

            return@newSuspendedTransaction fileIdentityList
        }
}

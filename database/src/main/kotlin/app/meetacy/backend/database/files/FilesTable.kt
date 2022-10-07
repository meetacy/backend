@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.files

import app.meetacy.backend.database.types.DatabaseFileDescription
import app.meetacy.backend.types.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class FilesTable(private val db: Database) : Table() {
    private val USER_ID = long("USER_ID")
    private val FILE_ID = long("FILE_ID").autoIncrement()
    private val ACCESS_HASH = varchar("ACCESS_HASH", 256)
    private val FILE_SIZE = long("FILE_SIZE").nullable()

    init {
        transaction(db) {
            SchemaUtils.create(this@FilesTable)
        }
    }

    suspend fun saveFileDescription(userId: UserId, accessHash: AccessHash): FileIdentity =
        newSuspendedTransaction(db = db) {
            val result = insert { statement ->
                statement[USER_ID] = userId.long
                statement[ACCESS_HASH] = accessHash.string
            }
            return@newSuspendedTransaction FileIdentity(
                FileId(result[FILE_ID]),
                accessHash
            )
        }

    suspend fun updateFileSize(userId: UserId, fileSize: FileSize) =
        newSuspendedTransaction(db = db) {
            update({ USER_ID eq userId.long }) { statement ->
                statement[FILE_SIZE] = fileSize.long
            }
        }

    suspend fun getFileDescription(fileId: FileId): DatabaseFileDescription? =
        newSuspendedTransaction(db = db) {
            val result = select { (FILE_ID eq fileId.long) }
                .firstOrNull() ?: return@newSuspendedTransaction null
            val userId = result[USER_ID]
            val fileSize = result[FILE_SIZE]?.let { fileSize -> FileSize(fileSize) }
            val fileIdentity = FileIdentity(
                FileId(result[FILE_ID]),
                AccessHash(result[ACCESS_HASH])
            )
            return@newSuspendedTransaction DatabaseFileDescription(UserId(userId), fileSize, fileIdentity)

        }
    
}

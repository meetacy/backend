@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.files

import app.meetacy.backend.database.types.DatabaseFileDescription
import app.meetacy.backend.types.FileId
import app.meetacy.backend.types.FileSize
import app.meetacy.backend.types.UserId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class FilesTable(private val db: Database) : Table() {
    private val USER_ID = long("USER_ID")
    private val FILE_ID = long("FILE_ID").autoIncrement()
    private val FILE_SIZE = long("FILE_SIZE").nullable()

    init {
        transaction(db) {
            SchemaUtils.create(this@FilesTable)
        }
    }

    suspend fun saveFileDescription(userId: UserId) =
        newSuspendedTransaction(db = db) {
            insert { statement ->
                statement[USER_ID] = userId.long
            }
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
            val fileSize = result[FILE_SIZE]
            return@newSuspendedTransaction if (fileSize != null) {
                DatabaseFileDescription(UserId(userId), FileSize(fileSize))
            } else DatabaseFileDescription(UserId(userId), null)
        }
    
}

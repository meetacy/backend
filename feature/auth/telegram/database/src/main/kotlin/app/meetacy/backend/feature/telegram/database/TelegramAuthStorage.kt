package app.meetacy.backend.feature.telegram.database

import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.prelogin.TemporaryTelegramHash
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object TelegramAuthTable : Table() {
    val TOKEN = varchar("TOKEN", AccessHash.LENGTH)
    val HASH = varchar("HASH", TemporaryTelegramHash.LENGTH)
}

class TelegramAuthStorage(private val db: Database) {

    suspend fun saveTelegramAuthInfo(
        temporalToken: AccessHash,
        telegramHash: TemporaryTelegramHash
    ) {
        newSuspendedTransaction(Dispatchers.IO, db) {
            TelegramAuthTable.insert { statement ->
                statement[TOKEN] = temporalToken.string
                statement[HASH] = telegramHash.string
            }
        }
    }
}

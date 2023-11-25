package app.meetacy.backend.feature.telegram.database

import app.meetacy.backend.feature.telegram.database.TelegramAuthTable.PERMANENT_TOKEN
import app.meetacy.backend.feature.telegram.database.TelegramAuthTable.PERMANENT_USER_ID
import app.meetacy.backend.feature.telegram.database.TelegramAuthTable.TEMPORAL_HASH
import app.meetacy.backend.feature.telegram.database.TelegramAuthTable.TEMPORAL_TOKEN
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.access.AccessToken
import app.meetacy.backend.types.auth.telegram.TemporaryTelegramHash
import app.meetacy.backend.types.users.UserId
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

object TelegramAuthTable : Table() {
    val TEMPORAL_TOKEN = varchar("TEMPORAL_TOKEN", AccessHash.LENGTH)
    val TEMPORAL_HASH = varchar("TEMPORAL_HASH", TemporaryTelegramHash.LENGTH)
    val PERMANENT_USER_ID = long("PERMANENT_USER_ID").nullable()
    val PERMANENT_TOKEN = varchar("PERMANENT_TOKEN", AccessToken.LENGTH).nullable()
}

class TelegramAuthStorage(private val db: Database) {

    suspend fun saveTelegramAuth(
        temporalToken: AccessToken,
        hash: TemporaryTelegramHash
    ) {
        newSuspendedTransaction(Dispatchers.IO, db) {
            TelegramAuthTable.insert { statement ->
                statement[TEMPORAL_TOKEN] = temporalToken.string
                statement[TEMPORAL_HASH] = hash.string
            }
        }
    }

    suspend fun checkTemporalHash(
        hash: TemporaryTelegramHash,
    ): Boolean = newSuspendedTransaction(Dispatchers.IO, db) {
        TelegramAuthTable.select { TEMPORAL_HASH eq hash.string }.any()
    }

    suspend fun saveAccessIdentity(
        accessIdentity: AccessIdentity,
        temporalHash: TemporaryTelegramHash
    ) {
        newSuspendedTransaction(Dispatchers.IO, db) {
            TelegramAuthTable.update({ TEMPORAL_HASH eq temporalHash.string }) { statement ->
                statement[PERMANENT_USER_ID] = accessIdentity.userId.long
                statement[PERMANENT_TOKEN] = accessIdentity.accessToken.string
            }
        }
    }

    suspend fun getAccessIdentity(
        temporalHash: TemporaryTelegramHash
    ): AccessIdentity? = newSuspendedTransaction(Dispatchers.IO, db) {
        TelegramAuthTable.select { TEMPORAL_HASH eq temporalHash.string }
            .firstOrNull()
            ?.toAccessIdentity()
    }

    suspend fun getTemporalHash(
        temporalToken: AccessToken
    ): TemporaryTelegramHash? = newSuspendedTransaction(Dispatchers.IO, db) {
        TelegramAuthTable.select {
            TEMPORAL_TOKEN eq temporalToken.string
        }
            .firstOrNull()
            ?.get(TEMPORAL_HASH)
            ?.let(::TemporaryTelegramHash)
    }

    private fun ResultRow.toAccessIdentity(): AccessIdentity? {
        return AccessIdentity(
            userId = UserId(long = this[PERMANENT_USER_ID] ?: return null),
            accessToken = AccessToken(string = this[PERMANENT_TOKEN] ?: return null)
        )
    }
}

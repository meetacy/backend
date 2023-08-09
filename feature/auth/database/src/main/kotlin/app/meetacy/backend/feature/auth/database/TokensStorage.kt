@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.feature.auth.database

import app.meetacy.backend.constants.HASH_LENGTH
import app.meetacy.backend.feature.auth.database.TokensTable.ACCESS_TOKEN
import app.meetacy.backend.feature.auth.database.TokensTable.OWNER_ID
import app.meetacy.backend.types.access.AccessIdentity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object TokensTable : Table() {
    val OWNER_ID = long("OWNER_ID")
    val ACCESS_TOKEN = varchar("ACCESS_TOKEN", HASH_LENGTH)
}

class TokensStorage(private val db: Database) {

    suspend fun addToken(accessIdentity: AccessIdentity) {
        newSuspendedTransaction(Dispatchers.IO, db) {
           with(accessIdentity) {
               TokensTable.insert { statement ->
                   statement[OWNER_ID] = userId.long
                   statement[ACCESS_TOKEN] = accessToken.string
               }
           }
       }
    }

    suspend fun checkToken(identity: AccessIdentity): Boolean = newSuspendedTransaction(Dispatchers.IO, db) {
        TokensTable.select {
            (ACCESS_TOKEN eq identity.accessToken.string) and
                    (OWNER_ID eq identity.userId.long)
        }.firstOrNull() != null
    }
}

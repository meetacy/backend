@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.auth

import app.meetacy.backend.types.HASH_LENGTH
import app.meetacy.backend.types.AccessIdentity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class TokensTable(private val db: Database) : Table() {
    private val OWNER_ID = long("OWNER_ID")
    private val ACCESS_TOKEN = varchar("ACCESS_TOKEN", length = HASH_LENGTH)

    init {
        transaction(db) {
            SchemaUtils.create(this@TokensTable)
        }
    }

    fun addToken(identity: AccessIdentity) {
       transaction(db) {
           insert { statement ->
               statement[OWNER_ID] = identity.userId.long
               statement[ACCESS_TOKEN] = identity.accessToken.string
           }
       }
    }

    fun checkToken(identity: AccessIdentity): Boolean = transaction (db) {
        select {
            (ACCESS_TOKEN eq identity.accessToken.string) and
                    (OWNER_ID eq identity.userId.long)
        }.firstOrNull() != null
    }
}

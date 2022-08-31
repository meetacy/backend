@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.auth

import app.meetacy.backend.database.types.DatabaseToken
import app.meetacy.backend.types.HASH_LENGTH
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserId
import app.meetacy.backend.types.UserIdentity
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

    fun addToken(
        userId: UserId,
        token: AccessToken
    ) {
       transaction(db) {
           insert { statement ->
               statement[OWNER_ID] = userId.long
               statement[ACCESS_TOKEN] = token.string
           }
       }
    }

    fun getToken(
        token: AccessToken
    ): DatabaseToken? = transaction (db) {
        val result = select{ ACCESS_TOKEN eq token.string }.firstOrNull() ?: return@transaction null
        val ownerId = result[OWNER_ID]
        return@transaction DatabaseToken(UserId(ownerId), token)
    }
}


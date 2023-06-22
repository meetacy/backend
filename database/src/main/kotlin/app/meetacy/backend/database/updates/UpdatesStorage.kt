package app.meetacy.backend.database.updates

import app.meetacy.backend.database.exposed.query.wrapTransactionAsFlow
import app.meetacy.backend.database.types.DatabaseUpdate
import app.meetacy.backend.database.updates.UpdatesTable.UNDERLYING_ID
import app.meetacy.backend.database.updates.UpdatesTable.UPDATE_ID
import app.meetacy.backend.database.updates.UpdatesTable.UPDATE_TYPE
import app.meetacy.backend.database.updates.UpdatesTable.USER_ID
import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.update.UpdateId
import app.meetacy.backend.types.user.UserId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object UpdatesTable : Table() {
    val UPDATE_ID = long("UPDATE_ID").autoIncrement()
    val USER_ID = long("USER_ID")
    val UPDATE_TYPE = enumeration<DatabaseUpdate.Type>("UPDATE_TYPE")
    val UNDERLYING_ID = long("UNDERLYING_ID")

    override val primaryKey = PrimaryKey(UPDATE_ID)
}

class UpdatesStorage(private val db: Database) {
    suspend fun addUpdate(
        userId: UserId,
        updateType: DatabaseUpdate.Type,
        underlyingId: Long
    ) {
        newSuspendedTransaction(Dispatchers.IO, db) {
            UpdatesTable.insert { statement ->
                statement[USER_ID] = userId.long
                statement[UPDATE_TYPE] = updateType
                statement[UNDERLYING_ID] = underlyingId
            }
        }
    }

    fun getPastUpdatesFlow(userId: UserId, fromId: UpdateId): Flow<DatabaseUpdate> =
        UsersTable.select {
            (USER_ID eq userId.long) and (UPDATE_ID greater fromId.long)
        }.wrapTransactionAsFlow(db).map { result ->
            DatabaseUpdate(
                id = UpdateId(result[UPDATE_ID]),
                type = result[UPDATE_TYPE],
                underlyingId = result[UNDERLYING_ID]
            )
        }
}

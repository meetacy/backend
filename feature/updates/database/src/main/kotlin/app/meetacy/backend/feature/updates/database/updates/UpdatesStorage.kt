package app.meetacy.backend.feature.updates.database.updates

import app.meetacy.backend.database.exposed.query.wrapTransactionAsFlow
import app.meetacy.backend.feature.updates.database.updates.UpdatesTable.UNDERLYING_ID
import app.meetacy.backend.feature.updates.database.updates.UpdatesTable.UPDATE_ID
import app.meetacy.backend.feature.updates.database.updates.UpdatesTable.UPDATE_TYPE
import app.meetacy.backend.feature.updates.database.updates.UpdatesTable.USER_ID
import app.meetacy.backend.feature.users.database.users.UsersTable
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.update.FullUpdate
import app.meetacy.backend.types.update.UpdateId
import app.meetacy.backend.types.users.UserId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object UpdatesTable : Table() {
    val UPDATE_ID = long("UPDATE_ID").autoIncrement()
    val USER_ID = long("USER_ID")
    val UPDATE_TYPE = enumeration<DatabaseUpdateType>("UPDATE_TYPE")
    val UNDERLYING_ID = long("UNDERLYING_ID")

    override val primaryKey = PrimaryKey(UPDATE_ID)
}

class UpdatesStorage(private val db: Database) {
    suspend fun addUpdate(
        userId: UserId,
        type: DatabaseUpdateType,
        underlyingId: Long
    ): UpdateId {
        val long = newSuspendedTransaction(Dispatchers.IO, db) {
            UpdatesTable.insert { statement ->
                statement[USER_ID] = userId.long
                statement[UPDATE_TYPE] = type
                statement[UNDERLYING_ID] = underlyingId
            }[UPDATE_ID]
        }

        return UpdateId(long)
    }

    fun getPastUpdatesFlow(userId: UserId, fromId: UpdateId): Flow<FullUpdate> =
        UsersTable.select {
            (USER_ID eq userId.long) and (UPDATE_ID greater fromId.long)
        }.wrapTransactionAsFlow(db).map { result ->
            when (result[UPDATE_TYPE]) {
                DatabaseUpdateType.Notification -> FullUpdate.Notification(
                    id = UpdateId(result[UPDATE_ID]),
                    notificationId = NotificationId(result[UNDERLYING_ID])
                )
            }
        }

}

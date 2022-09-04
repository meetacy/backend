@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.notifications

import app.meetacy.backend.database.types.DatabaseNotification
import app.meetacy.backend.types.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class NotificationsTable(private val db: Database) : Table() {
    private val NOTIFICATION_ID = long("NOTIFICATION_ID")
    private val OWNER_ID = long("OWNER_ID")
    private val TYPE = enumeration("TYPE", DatabaseNotification.Type::class)
    private val DATE = varchar("DATE", length = DATA_MAX_LIMIT)
    private val INVITER_ID = long("INVITER_ID").nullable()
    private val SUBSCRIBED_ID = long("SUBSCRIBED_ID").nullable().default(null)
    private val INVITED_MEETING_ID = long("INVITED_MEETING_ID").nullable().default(null)

    init {
        transaction(db) {
            SchemaUtils.create(this@NotificationsTable)
        }
    }

    suspend fun addNotification(notification: DatabaseNotification) =
        newSuspendedTransaction(db = db) {
            with(notification) {
                insert { statement ->
                    statement[NOTIFICATION_ID] = id.long
                    statement[OWNER_ID] = ownerId.long
                    statement[TYPE] = type
                    statement[DATE] = date.iso8601
                    statement[INVITER_ID] = inviterId?.long
                    statement[SUBSCRIBED_ID] = subscriberId?.long
                    statement[INVITED_MEETING_ID] = invitedMeetingId?.long
                }
            }
        }

    suspend fun isNotificationExists(notificationId: NotificationId): Boolean {
        val result = newSuspendedTransaction(db = db) {
            select{ (NOTIFICATION_ID eq notificationId.long) }
                .firstOrNull()
        }
        return result != null

    }

    suspend fun getNotifications(ownerId: UserId, offset: Long, amount: Int): List<DatabaseNotification> =
        newSuspendedTransaction(db = db) {
            select { (OWNER_ID eq ownerId.long) }
                .limit(amount, offset)
                .map { it ->
                    DatabaseNotification(
                        NotificationId(it[NOTIFICATION_ID]),
                        UserId(it[OWNER_ID]),
                        it[TYPE],
                        Date(it[DATE]),
                        it[INVITER_ID]?.let { UserId(it) },
                        it[SUBSCRIBED_ID]?.let { UserId(it) },
                        it[INVITED_MEETING_ID]?.let { MeetingId(it) }
                    )
                }
        }
}

@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.notifications

import app.meetacy.backend.database.notifications.NotificationsTable.NOTIFICATION_ID
import app.meetacy.backend.database.notifications.NotificationsTable.OWNER_ID
import app.meetacy.backend.database.notifications.NotificationsTable.TYPE
import app.meetacy.backend.database.notifications.NotificationsTable.DATE
import app.meetacy.backend.database.notifications.NotificationsTable.INVITER_ID
import app.meetacy.backend.database.notifications.NotificationsTable.SUBSCRIBED_ID
import app.meetacy.backend.database.notifications.NotificationsTable.INVITED_MEETING_ID
import app.meetacy.backend.database.types.DatabaseNotification
import app.meetacy.backend.types.*
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.user.UserId
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object NotificationsTable : Table() {
    val NOTIFICATION_ID = long("NOTIFICATION_ID")
    val OWNER_ID = long("OWNER_ID")
    val TYPE = enumeration("TYPE", DatabaseNotification.Type::class)
    val DATE = varchar("DATE", length = DATE_TIME_MAX_LIMIT)
    val INVITER_ID = long("INVITER_ID").nullable()
    val SUBSCRIBED_ID = long("SUBSCRIBED_ID").nullable().default(null)
    val INVITED_MEETING_ID = long("INVITED_MEETING_ID").nullable().default(null)
}

class NotificationsStorage(private val db: Database) {

    suspend fun addNotification(notification: DatabaseNotification) =
        newSuspendedTransaction(Dispatchers.IO, db) {
            with(notification) {
                NotificationsTable.insert { statement ->
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
        val result = newSuspendedTransaction(Dispatchers.IO, db) {
            NotificationsTable.select{ (NOTIFICATION_ID eq notificationId.long) }
                .firstOrNull()
        }
        return result != null

    }

    suspend fun getNotifications(ownerId: UserId, offset: Long, amount: Int): List<DatabaseNotification> =
        newSuspendedTransaction(Dispatchers.IO, db) {
            NotificationsTable.select { (OWNER_ID eq ownerId.long) }
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

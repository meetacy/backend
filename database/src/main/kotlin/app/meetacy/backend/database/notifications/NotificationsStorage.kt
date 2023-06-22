@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.notifications

import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.database.notifications.NotificationsTable.DATE
import app.meetacy.backend.database.notifications.NotificationsTable.INVITED_MEETING_ID
import app.meetacy.backend.database.notifications.NotificationsTable.INVITER_ID
import app.meetacy.backend.database.notifications.NotificationsTable.NOTIFICATION_ID
import app.meetacy.backend.database.notifications.NotificationsTable.OWNER_ID
import app.meetacy.backend.database.notifications.NotificationsTable.SUBSCRIBED_ID
import app.meetacy.backend.database.notifications.NotificationsTable.TYPE
import app.meetacy.backend.database.paging
import app.meetacy.backend.database.types.DatabaseNotification
import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.DATE_TIME_MAX_LIMIT
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.annotation.UnsafeConstructor
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.paging.pagingResultLong
import app.meetacy.backend.types.user.UserId
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object NotificationsTable : Table() {
    val NOTIFICATION_ID = long("NOTIFICATION_ID")
    val OWNER_ID = reference("OWNER_ID", UsersTable.USER_ID)
    val TYPE = enumeration("TYPE", DatabaseNotification.Type::class)
    val DATE = varchar("DATE", length = DATE_TIME_MAX_LIMIT)
    val INVITER_ID = reference("INVITER_ID", UsersTable.USER_ID).nullable()
    val SUBSCRIBED_ID = reference("SUBSCRIBED_ID", UsersTable.USER_ID).nullable().default(null)
    val INVITED_MEETING_ID = reference("INVITED_MEETING_ID", MeetingsTable.MEETING_ID).nullable().default(null)
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

    suspend fun getNotifications(
        ownerId: UserId,
        pagingId: PagingId?,
        amount: Amount
    ): PagingResult<DatabaseNotification> = newSuspendedTransaction(Dispatchers.IO, db) {
        NotificationsTable.select {
            (OWNER_ID eq ownerId.long) and (NOTIFICATION_ID paging pagingId)
        }
            .limit(amount.int)
            .map { it.toNotification() }
            .pagingResultLong(amount) { it.id.long }
    }

    suspend fun getNotificationsOrNull(
        notificationIds: List<NotificationId>
    ): List<DatabaseNotification?> = newSuspendedTransaction(Dispatchers.IO, db) {
        val ids = notificationIds.map { it.long }

        val foundNotifications = NotificationsTable.select { (NOTIFICATION_ID inList ids) }
            .map { it.toNotification() }
            .associateBy { notification -> notification.id }

        notificationIds.map { notificationId -> foundNotifications[notificationId] }
    }

    suspend fun getNotifications(
        notificationIds: List<NotificationId>
    ): List<DatabaseNotification> {
        val notifications = getNotificationsOrNull(notificationIds).filterNotNull()
        require(notifications.size == notificationIds.size) { "Cannot find all notifications from list $notificationIds" }
        return notifications
    }

    suspend fun getNotificationOrNull(id: NotificationId): DatabaseNotification? =
        getNotificationsOrNull(listOf(id)).first()

    suspend fun getNotification(id: NotificationId): DatabaseNotification =
        getNotificationOrNull(id) ?: error("Notification with id $id not found")

    @OptIn(UnsafeConstructor::class)
    private fun ResultRow.toNotification(): DatabaseNotification =
        DatabaseNotification(
            NotificationId(this[NOTIFICATION_ID]),
            UserId(this[OWNER_ID]),
            this[TYPE],
            Date(this[DATE]),
            this[INVITER_ID]?.let(::UserId),
            this[SUBSCRIBED_ID]?.let(::UserId),
            this[INVITED_MEETING_ID]?.let(::MeetingId)
        )
}

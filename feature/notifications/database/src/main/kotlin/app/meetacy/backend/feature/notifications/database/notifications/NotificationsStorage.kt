@file:Suppress("PrivatePropertyName", "OPT_IN_USAGE")

package app.meetacy.backend.feature.notifications.database.notifications

import app.meetacy.backend.constants.DATE_TIME_MAX_LIMIT
import app.meetacy.backend.feature.meetings.database.meetings.MeetingsTable
import app.meetacy.backend.feature.notifications.database.notifications.NotificationsTable.DATE
import app.meetacy.backend.feature.notifications.database.notifications.NotificationsTable.INVITER_ID
import app.meetacy.backend.feature.notifications.database.notifications.NotificationsTable.MEETING_ID
import app.meetacy.backend.feature.notifications.database.notifications.NotificationsTable.NOTIFICATION_ID
import app.meetacy.backend.feature.notifications.database.notifications.NotificationsTable.OWNER_ID
import app.meetacy.backend.feature.notifications.database.notifications.NotificationsTable.SUBSCRIBED_ID
import app.meetacy.backend.feature.notifications.database.notifications.NotificationsTable.TYPE
import app.meetacy.backend.feature.notifications.database.notifications.NotificationsTable.Type
import app.meetacy.backend.database.paging
import app.meetacy.backend.feature.notifications.database.types.DatabaseNotification
import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.paging.pagingResultLong
import app.meetacy.backend.types.users.UserId
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object NotificationsTable : Table() {
    val NOTIFICATION_ID = long("NOTIFICATION_ID").autoIncrement()
    val OWNER_ID = reference("OWNER_ID", UsersTable.USER_ID)
    val TYPE = enumeration("TYPE", Type::class)
    val DATE = varchar("DATE", length = DATE_TIME_MAX_LIMIT)
    val INVITER_ID = reference("INVITER_ID", UsersTable.USER_ID).nullable()
    val SUBSCRIBED_ID = reference("SUBSCRIBED_ID", UsersTable.USER_ID).nullable().default(null)
    val MEETING_ID = reference("INVITED_MEETING_ID", MeetingsTable.MEETING_ID).nullable().default(null)

    override val primaryKey = PrimaryKey(NOTIFICATION_ID)

    enum class Type {
        Subscription, Invitation
    }
}

class NotificationsStorage(private val db: Database) {

    suspend fun addSubscriptionNotification(
        userId: UserId,
        subscriberId: UserId,
        date: DateTime
    ): NotificationId {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            NotificationsTable.insert { statement ->
                statement[TYPE] = Type.Subscription
                statement[OWNER_ID] = userId.long
                statement[DATE] = date.iso8601
                statement[SUBSCRIBED_ID] = subscriberId.long
            }[NOTIFICATION_ID].let(::NotificationId)
        }
    }

    suspend fun addInvitationNotification(
        userId: UserId,
        inviterId: UserId,
        meetingId: MeetingId,
        date: DateTime
    ): NotificationId {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            NotificationsTable.insert { statement ->
                statement[TYPE] = Type.Invitation
                statement[OWNER_ID] = userId.long
                statement[DATE] = date.iso8601
                statement[INVITER_ID] = inviterId.long
                statement[MEETING_ID] = meetingId.long
            }[NOTIFICATION_ID].let(::NotificationId)
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

    private fun ResultRow.toNotification(): DatabaseNotification =
        when (this[TYPE]) {
            Type.Subscription -> DatabaseNotification.Subscription(
                id = NotificationId(this[NOTIFICATION_ID]),
                ownerId = UserId(this[OWNER_ID]),
                date = DateTime(this[DATE]),
                subscriberId = UserId(this[SUBSCRIBED_ID]!!)
            )
            Type.Invitation -> DatabaseNotification.Invitation(
                id = NotificationId(this[NOTIFICATION_ID]),
                ownerId = UserId(this[OWNER_ID]),
                date = DateTime(this[DATE]),
                inviterId = UserId(this[INVITER_ID]!!),
                meetingId = MeetingId(this[MEETING_ID]!!)
            )
        }
}

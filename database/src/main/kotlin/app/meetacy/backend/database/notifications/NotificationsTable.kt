@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.notifications

import app.meetacy.backend.database.types.DatabaseNotification
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import kotlin.reflect.KClass

class NotificationsTable(private val db: Database) : Table() {
    private val NOTIFICATION_ID = long("NOTIFICATION_ID")
    private val OWNER_ID = long("OWNER_ID")
    private val TYPE = enumeration("TYPE", DatabaseNotification.Type::class)

}

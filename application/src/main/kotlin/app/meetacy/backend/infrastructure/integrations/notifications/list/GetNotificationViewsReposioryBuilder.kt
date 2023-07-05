package app.meetacy.backend.infrastructure.integrations.notifications.list

import app.meetacy.backend.database.integration.types.GetNotificationsViewsRepository
import app.meetacy.backend.infrastructure.integrations.meetings.get.getMeetingsViewsRepository
import app.meetacy.backend.infrastructure.integrations.users.get.getUserViewsRepository
import org.jetbrains.exposed.sql.Database

fun getNotificationViewsRepository(db: Database) = GetNotificationsViewsRepository(
    db = db,
    getMeetingsViewsRepository = getMeetingsViewsRepository(db),
    getUsersViewsRepository = getUserViewsRepository(db)
)

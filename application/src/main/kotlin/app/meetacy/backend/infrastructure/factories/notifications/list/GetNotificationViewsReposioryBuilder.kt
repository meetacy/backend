package app.meetacy.backend.infrastructure.factories.notifications.list

import app.meetacy.backend.database.integration.types.GetNotificationsViewsRepository
import app.meetacy.backend.infrastructure.factories.meetings.get.getMeetingsViewsRepository
import app.meetacy.backend.infrastructure.factories.users.get.getUserViewsRepository
import org.jetbrains.exposed.sql.Database

fun getNotificationViewsRepository(db: Database) = GetNotificationsViewsRepository(
    db = db,
    getMeetingsViewsRepository = getMeetingsViewsRepository(db),
    getUsersViewsRepository = getUserViewsRepository(db)
)

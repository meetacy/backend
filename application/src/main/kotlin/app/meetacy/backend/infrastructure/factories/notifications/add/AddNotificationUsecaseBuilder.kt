package app.meetacy.backend.infrastructure.factories.notifications.add

import app.meetacy.backend.database.integration.notifications.AddNotificationUsecase
import app.meetacy.backend.database.integration.updates.stream.UpdatesMiddleware
import app.meetacy.backend.usecase.invitations.add.AddNotificationUsecase
import app.meetacy.backend.usecase.updates.stream.UpdatesMiddleware
import org.jetbrains.exposed.sql.Database

fun addNotificationUsecase(db: Database): AddNotificationUsecase = AddNotificationUsecase(db, updatesMiddlewareBuilder(db))

fun updatesMiddlewareBuilder(db: Database): UpdatesMiddleware = UpdatesMiddleware(db)

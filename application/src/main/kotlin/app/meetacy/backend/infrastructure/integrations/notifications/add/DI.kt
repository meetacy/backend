@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.notifications.add

import app.meetacy.backend.database.integration.notifications.AddNotificationUsecase
import app.meetacy.backend.database.integration.updates.stream.UpdatesMiddleware
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.usecase.invitations.add.AddNotificationUsecase
import app.meetacy.backend.usecase.updates.stream.UpdatesMiddleware

val DI.addNotificationUsecase: AddNotificationUsecase by Dependency
val DI.updatesMiddleware: UpdatesMiddleware by Dependency

fun DIBuilder.updatesMiddleware() {
    val updatesMiddleware by singleton<UpdatesMiddleware> {
        UpdatesMiddleware(database)
    }
}

fun DIBuilder.addNotificationUsecase() {
    updatesMiddleware()
    val addNotificationUsecase by singleton<AddNotificationUsecase> {
        AddNotificationUsecase(database, updatesMiddleware)
    }
}

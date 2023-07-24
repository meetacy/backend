package app.meetacy.backend.infrastructure.integrations.notifications.add

import app.meetacy.backend.database.integration.notifications.AddNotificationUsecase
import app.meetacy.backend.database.integration.updates.stream.UpdatesMiddleware
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.usecase.notifications.add.AddNotificationUsecase
import app.meetacy.backend.usecase.updates.stream.UpdatesMiddleware
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

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
        AddNotificationUsecase(
            database,
            updatesMiddleware
        )
    }
}

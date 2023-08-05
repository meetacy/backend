package app.meetacy.backend.infrastructure.integrations.notifications.add

import app.meetacy.backend.database.integration.notifications.AddNotificationUsecase
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.database.updates.updatesMiddleware
import app.meetacy.backend.usecase.notifications.add.AddNotificationUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.addNotificationUsecase: AddNotificationUsecase by Dependency

fun DIBuilder.addNotificationUsecase() {
    val addNotificationUsecase by singleton<AddNotificationUsecase> {
        AddNotificationUsecase(
            database,
            updatesMiddleware
        )
    }
}

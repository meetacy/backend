package app.meetacy.backend.infrastructure.database.notifications.add

import app.meetacy.backend.feature.notifications.database.integration.notifications.AddNotificationUsecase
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.database.updates.updatesMiddleware
import app.meetacy.backend.feature.notifications.usecase.notifications.add.AddNotificationUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.addNotificationUsecase: AddNotificationUsecase by Dependency

fun DIBuilder.addNotification() {
    val addNotificationUsecase by singleton<AddNotificationUsecase> {
        AddNotificationUsecase(
            database,
            updatesMiddleware
        )
    }
}

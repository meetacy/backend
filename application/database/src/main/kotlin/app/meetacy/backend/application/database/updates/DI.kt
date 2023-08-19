package app.meetacy.backend.application.database.updates

import app.meetacy.backend.feature.updates.database.integration.updates.stream.DatabaseStreamUpdatesUsecaseStorage
import app.meetacy.backend.feature.updates.database.integration.updates.stream.UpdatesMiddleware
import app.meetacy.backend.feature.updates.database.updates.UpdatesStorage
import app.meetacy.backend.application.database.database
import app.meetacy.backend.feature.updates.usecase.updates.stream.StreamUpdatesUsecase
import app.meetacy.backend.feature.updates.usecase.updates.stream.UpdatesMiddleware
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.updatesStorage: UpdatesStorage by Dependency
val DI.updatesMiddleware: UpdatesMiddleware by Dependency
val DI.streamUpdatesStorage: StreamUpdatesUsecase.Storage by Dependency

fun DIBuilder.updates() {
    updatesMiddleware()
    val updatesStorage by singleton { UpdatesStorage(database) }
    val streamUpdatesUsecaseStorage by singleton<StreamUpdatesUsecase.Storage> {
        DatabaseStreamUpdatesUsecaseStorage(updatesMiddleware)
    }
}

fun DIBuilder.updatesMiddleware() {
    val updatesMiddleware by singleton<UpdatesMiddleware> {
        UpdatesMiddleware(database)
    }
}

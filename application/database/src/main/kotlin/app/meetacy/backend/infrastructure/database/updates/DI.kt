package app.meetacy.backend.infrastructure.database.updates

import app.meetacy.backend.database.integration.updates.stream.UpdatesMiddleware
import app.meetacy.backend.database.updates.UpdatesStorage
import app.meetacy.backend.feature.auth.usecase.updates.stream.UpdatesMiddleware
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.updatesStorage: UpdatesStorage by Dependency
val DI.updatesMiddleware: UpdatesMiddleware by Dependency

fun DIBuilder.updates() {
    updatesMiddleware()
    val updatesStorage by singleton { UpdatesStorage(database) }
}

fun DIBuilder.updatesMiddleware() {
    val updatesMiddleware by singleton<UpdatesMiddleware> {
        UpdatesMiddleware(database)
    }
}

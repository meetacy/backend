package app.meetacy.backend.application.endpoints.updates

import app.meetacy.backend.feature.updates.endpoints.updates.UpdatesDependencies
import app.meetacy.backend.application.usecase.updates.stream.streamUpdatesRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.updatesDependencies: UpdatesDependencies by Dependency

fun DIBuilder.updates() {
    streamUpdatesRepository()
    val updatesDependencies by singleton {
        UpdatesDependencies(
            streamUpdatesRepository
        )
    }
}

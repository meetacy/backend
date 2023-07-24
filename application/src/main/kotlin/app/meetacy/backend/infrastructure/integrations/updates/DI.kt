@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.updates

import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency
import app.meetacy.backend.endpoint.updates.UpdatesDependencies
import app.meetacy.backend.infrastructure.integrations.updates.stream.streamUpdatesRepository

val DI.updatesDependencies: UpdatesDependencies by Dependency

fun DIBuilder.updates() {
    streamUpdatesRepository()
    val updatesDependencies by singleton {
        UpdatesDependencies(
            streamUpdatesRepository
        )
    }
}

@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.updates

import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
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

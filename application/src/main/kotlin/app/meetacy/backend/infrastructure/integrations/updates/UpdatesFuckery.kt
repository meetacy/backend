package app.meetacy.backend.infrastructure.integrations.updates

import app.meetacy.backend.endpoint.updates.UpdatesDependencies
import app.meetacy.backend.infrastructure.integrations.updates.stream.streamUpdatesRepository
import org.jetbrains.exposed.sql.Database

fun updatesDependenciesFactory(
    db: Database
): UpdatesDependencies = UpdatesDependencies(
    streamUpdatesRepository = streamUpdatesRepository(db)
)

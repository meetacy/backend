package app.meetacy.database.updater

import app.meetacy.database.updater.context.MigrationContext

public interface DatabaseInitializer {
    public suspend fun MigrationContext.initialize()

    public object Empty : DatabaseInitializer {
        override suspend fun MigrationContext.initialize() {}
    }
}

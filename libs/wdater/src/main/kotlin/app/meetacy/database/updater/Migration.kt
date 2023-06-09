package app.meetacy.database.updater

import app.meetacy.database.updater.context.MigrationContext

public interface Migration {

    public val fromVersion: Int

    public val toVersion: Int get() = fromVersion + 1

    public suspend fun MigrationContext.migrate()
}

public fun Migration.asInitializer(): DatabaseInitializer {
    return object : DatabaseInitializer {
        override suspend fun MigrationContext.initialize() {
            migrate()
        }
    }
}

package app.meetacy.database.updater

public interface DatabaseInitializer {
    public suspend fun MigrationContext.initialize()

    public object Empty : DatabaseInitializer {
        override suspend fun MigrationContext.initialize() {}
    }
}

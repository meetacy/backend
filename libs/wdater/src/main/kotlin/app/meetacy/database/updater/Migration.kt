package app.meetacy.database.updater

public interface Migration {

    public val fromVersion: Int

    public val toVersion: Int get() = fromVersion + 1

    public suspend fun MigrationContext.migrate()
}

package app.meetacy.database.updater

import org.jetbrains.exposed.sql.Database

public class WdaterConfig(
    public val database: Database? = null,
    public val storage: WdaterStorage = WdaterTable(database = database),
    public val defaultSchemaVersion: Int = 0,
    public val initializer: DatabaseInitializer = DatabaseInitializer.Empty
) {
    public fun builder(): Builder = Builder(database, storage, defaultSchemaVersion)

    public class Builder(
        public var database: Database? = null,
        public var storage: WdaterStorage = WdaterTable(database = database),
        public var defaultSchemaVersion: Int = 0,
        public var initializer: DatabaseInitializer = DatabaseInitializer.Empty
    ) {
        public fun tableStorage(
            name: String = "migrations",
            database: Database? = this.database
        ): WdaterStorage {
            return WdaterTable(name, database)
        }

        public inline fun initializer(crossinline block: suspend () -> Unit) {
            this.initializer = object : DatabaseInitializer {
                override suspend fun MigrationContext.initialize() {
                    block()
                }
            }
        }

        public fun build(): WdaterConfig = WdaterConfig(
            database = database,
            storage = storage,
            defaultSchemaVersion = defaultSchemaVersion,
            initializer = initializer
        )
    }
}

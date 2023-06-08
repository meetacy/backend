package app.meetacy.database.updater

import org.jetbrains.exposed.sql.Database

public class WdaterConfig(
    public val database: Database? = null,
    public val storage: WdaterStorage = WdaterTable(database = database),
    public val defaultSchemaVersion: Int = 0
) {
    public fun builder(): Builder = Builder(database, storage, defaultSchemaVersion)

    public class Builder(
        public var database: Database? = null,
        public var storage: WdaterStorage = WdaterTable(database = database),
        public var defaultSchemaVersion: Int = 0
    ) {
        public fun tableStorage(
            name: String = "migrations",
            database: Database? = this.database
        ): WdaterStorage {
            return WdaterTable(name, database)
        }

        public fun build(): WdaterConfig = WdaterConfig(database, storage, defaultSchemaVersion)
    }
}

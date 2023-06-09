package app.meetacy.database.updater

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

public fun Wdater(block: WdaterConfig.Builder.() -> Unit): Wdater {
    val config = WdaterConfig().builder().apply(block).build()
    return Wdater(config)
}

public class Wdater(private val config: WdaterConfig = WdaterConfig()) {
    private val db = config.database
    private val storage = config.storage

    public suspend fun update(vararg migrations: Migration) {
        update(migrations.toList())
    }

    public suspend fun update(migrations: List<Migration>) {
        val fromVersion = storage.getSchemaVersion()

        // skip migrations if there is no version
        if (fromVersion == null) {
            initializeDatabase(migrations)
        } else {
            val migratedVersion = migrate(fromVersion, migrations)
            storage.setSchemaVersion(migratedVersion)
        }
    }

    private suspend fun initializeDatabase(migrations: List<Migration>) {
        val maxVersion = migrations.maxOfOrNull { it.toVersion } ?: config.defaultSchemaVersion
        storage.setSchemaVersion(maxVersion)
        newSuspendedTransaction(Dispatchers.IO, db) {
            with(config.initializer) {
                MigrationContext(transaction = this@newSuspendedTransaction).initialize()
            }
        }
    }

    /**
     * @return the resulted version table schema is on after migration
     */
    private tailrec suspend fun migrate(
        fromVersion: Int,
        migrations: List<Migration>
    ): Int {
        // If we did not find any migration to perform, assume we should stop there
        val migration = migrations.find { migration ->
            migration.fromVersion == fromVersion
        } ?: return fromVersion

        newSuspendedTransaction(Dispatchers.IO, db) {
            with (migration) {
                MigrationContext(transaction = this@newSuspendedTransaction).migrate()
            }
        }

        return migrate(migration.toVersion, migrations)
    }

    public fun config(block: WdaterConfig.Builder.() -> Unit): Wdater {
        return Wdater(config.builder().apply(block).build())
    }
}

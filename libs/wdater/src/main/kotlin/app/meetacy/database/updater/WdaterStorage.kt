package app.meetacy.database.updater

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

public interface WdaterStorage {
    public suspend fun getSchemaVersion(): Int?
    public suspend fun setSchemaVersion(version: Int)
}

@Suppress("PropertyName")
public class WdaterTable(
    name: String = "migrations",
    private val database: Database? = null
) : Table(name), WdaterStorage {
    public val SCHEMA_VERSION: Column<Int?> = integer("version").nullable().default(null)

    init {
        transaction(database) {
            SchemaUtils.create(this@WdaterTable)
        }
    }

    override suspend fun getSchemaVersion(): Int? {
        return newSuspendedTransaction(Dispatchers.IO, database) {
            selectAll().firstOrNull()?.get(SCHEMA_VERSION)
        }
    }

    override suspend fun setSchemaVersion(version: Int) {
        newSuspendedTransaction(Dispatchers.IO, database) {
            deleteAll()
            insert { it[SCHEMA_VERSION] = version }
        }
    }
}

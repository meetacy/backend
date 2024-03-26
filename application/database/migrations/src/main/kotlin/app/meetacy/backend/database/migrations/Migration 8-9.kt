package app.meetacy.backend.database.migrations

import app.meetacy.backend.constants.ACCESS_HASH_LENGTH
import app.meetacy.backend.constants.ACCESS_TOKEN_LENGTH
import app.meetacy.backend.feature.files.database.FilesTable
import app.meetacy.backend.feature.meetings.database.meetings.MeetingsTable
import app.meetacy.backend.feature.users.database.users.UsersTable
import app.meetacy.database.updater.Migration
import app.meetacy.database.updater.MigrationContext
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

object `Migration 8-9` : Migration {
    override val fromVersion = 8

    override suspend fun MigrationContext.migrate() {
        migrateFiles()
        migrateMeetings()
        migrateUsers()
    }

    private fun MigrationContext.migrateFiles() {
        val oldAccessHash = FilesTable.ACCESS_HASH

        val accessHashes = FilesTable.selectAll().associate { result ->
            val id = result[FilesTable.FILE_ID]
            val accessHash = result[FilesTable.ACCESS_HASH]
            id to accessHash
        }

        oldAccessHash.drop()
        FilesTable.ACCESS_HASH.create("")

        accessHashes.forEach { (id, accessHash) ->
            FilesTable.update({ FilesTable.FILE_ID eq id }) { statement ->
                statement[ACCESS_HASH] = accessHash.take(ACCESS_HASH_LENGTH)
            }
        }
    }

    private fun MigrationContext.migrateMeetings() {
        val oldAccessHash = MeetingsTable.ACCESS_HASH

        val accessHashes = MeetingsTable.selectAll().associate { result ->
            val id = result[MeetingsTable.MEETING_ID]
            val accessHash = result[MeetingsTable.ACCESS_HASH]
            id to accessHash
        }

        oldAccessHash.drop()
        MeetingsTable.ACCESS_HASH.create("")

        accessHashes.forEach { (id, accessHash) ->
            MeetingsTable.update({ MeetingsTable.MEETING_ID eq id }) { statement ->
                statement[ACCESS_HASH] = accessHash.take(ACCESS_HASH_LENGTH)
            }
        }
    }

    private fun MigrationContext.migrateUsers() {
        val oldAccessHash = UsersTable.ACCESS_HASH

        val accessHashes = UsersTable.selectAll().associate { result ->
            val id = result[UsersTable.USER_ID]
            val accessHash = result[UsersTable.ACCESS_HASH]
            id to accessHash
        }

        oldAccessHash.drop()
        UsersTable.ACCESS_HASH.create("")

        accessHashes.forEach { (id, accessHash) ->
            UsersTable.update({ UsersTable.USER_ID eq id }) { statement ->
                statement[ACCESS_HASH] = accessHash.take(ACCESS_HASH_LENGTH)
            }
        }
    }
}

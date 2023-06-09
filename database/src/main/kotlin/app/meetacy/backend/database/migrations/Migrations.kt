package app.meetacy.backend.database.migrations

import app.meetacy.backend.database.auth.TokensTable
import app.meetacy.backend.database.email.ConfirmationTable
import app.meetacy.backend.database.files.FilesTable
import app.meetacy.backend.database.friends.FriendsTable
import app.meetacy.backend.database.invitations.InvitationsTable
import app.meetacy.backend.database.location.UsersLocationsTable
import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.database.notifications.LastReadNotificationsTable
import app.meetacy.backend.database.notifications.NotificationsTable
import app.meetacy.backend.database.users.UsersTable
import app.meetacy.database.updater.Migration
import app.meetacy.database.updater.Wdater
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

private val migrations = listOf<Migration>(`Migration 0-1`)

suspend fun runMigrations(db: Database) {
    val wdater = Wdater {
        database = db
        initializer { createTables(db) }
    }
    wdater.update(migrations)
}

private suspend fun createTables(db: Database) {
    newSuspendedTransaction(Dispatchers.IO, db) {
        SchemaUtils.create(
            TokensTable, UsersTable,
            ConfirmationTable, FilesTable,
            InvitationsTable, UsersLocationsTable,
            MeetingsTable, ParticipantsTable,
            LastReadNotificationsTable, NotificationsTable,
            FriendsTable
        )
    }
}

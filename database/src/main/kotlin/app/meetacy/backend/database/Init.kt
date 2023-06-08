package app.meetacy.backend.database

import app.meetacy.backend.database.auth.TokensTable
import app.meetacy.backend.database.email.ConfirmationTable
import app.meetacy.backend.database.files.FilesTable
import app.meetacy.backend.database.friends.FriendsTable
import app.meetacy.backend.database.invitations.InvitationsTable
import app.meetacy.backend.database.location.UsersLocationsTable
import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.database.migrations.runMigrations
import app.meetacy.backend.database.notifications.LastReadNotificationsTable
import app.meetacy.backend.database.notifications.NotificationsTable
import app.meetacy.backend.database.users.UsersTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun initDatabase(db: Database) {
    runMigrations(db)
    createTables(db)
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

package app.meetacy.backend.database

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
import app.meetacy.backend.database.updates.UpdatesTable
import app.meetacy.backend.database.users.UsersTable
import org.jetbrains.exposed.sql.Table

val tables: List<Table> = listOf(
    TokensTable, UsersTable,
    ConfirmationTable, FilesTable,
    InvitationsTable, UsersLocationsTable,
    MeetingsTable, ParticipantsTable,
    LastReadNotificationsTable, NotificationsTable,
    FriendsTable, UpdatesTable
).apply {
    require(distinctBy { table -> table.tableName }.size == size) { "There were duplicates in `tables` list" }
}

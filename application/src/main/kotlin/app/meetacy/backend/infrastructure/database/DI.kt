package app.meetacy.backend.infrastructure.database

import app.meetacy.backend.infrastructure.database.auth.auth
import app.meetacy.backend.infrastructure.database.email.email
import app.meetacy.backend.infrastructure.database.files.files
import app.meetacy.backend.infrastructure.database.friends.friends
import app.meetacy.backend.infrastructure.database.invitations.invitations
import app.meetacy.backend.infrastructure.database.meetings.meetings
import app.meetacy.backend.infrastructure.database.notifications.notifications
import app.meetacy.backend.infrastructure.database.updates.updates
import app.meetacy.backend.infrastructure.database.users.users
import app.meetacy.backend.infrastructure.databaseConfig
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency
import org.jetbrains.exposed.sql.Database

data class DatabaseConfig(
    val url: String,
    val user: String,
    val password: String
)

val DI.database: Database by Dependency

fun DIBuilder.database() {
    singleton()
    // storages
    auth()
    email()
    files()
    friends()
    invitations()
    meetings()
    notifications()
    updates()
    users()
}

private fun DIBuilder.singleton() {
    val database by singleton {
        with(databaseConfig) {
            Database.connect(
                url = url,
                user = user,
                password = password
            )
        }
    }
}

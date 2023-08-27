package app.meetacy.backend.application.database

import app.meetacy.backend.feature.auth.database.integration.auth
import app.meetacy.backend.feature.email.database.integration.email
import app.meetacy.backend.feature.files.database.integration.files
import app.meetacy.backend.feature.friends.usecase.integration.friends
import app.meetacy.backend.feature.invitations.usecase.integration.invitations
import app.meetacy.backend.feature.meetings.database.integration.meetings
import app.meetacy.backend.feature.users.usecase.integration.users
import app.meetacy.di.builder.DIBuilder
import org.jetbrains.exposed.sql.Database

data class DatabaseConfig(
    val url: String,
    val user: String,
    val password: String,
    val isTest: Boolean
)

fun DIBuilder.database() {
    databaseSingleton()
    // storages

    auth()
    email()
    files()
    friends()
    invitations()
    meetings()
    users()
}

private fun DIBuilder.databaseSingleton() {
    val database by singleton {
        val databaseConfig: DatabaseConfig by getting

        with(databaseConfig) {
            Database.connect(
                url = url,
                user = user,
                password = password
            )
        }
    }
}

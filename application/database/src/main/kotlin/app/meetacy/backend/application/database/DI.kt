package app.meetacy.backend.application.database

import app.meetacy.backend.feature.auth.database.integration.auth
import app.meetacy.backend.feature.email.database.integration.email
import app.meetacy.backend.feature.files.database.integration.files
import app.meetacy.backend.feature.friends.database.integration.friends
import app.meetacy.backend.feature.invitations.database.integration.invitations
import app.meetacy.backend.feature.meetings.database.integration.meetings
import app.meetacy.backend.feature.notifications.database.integration.notifications
import app.meetacy.backend.feature.telegram.database.integration.telegram
import app.meetacy.backend.feature.updates.database.integration.updates
import app.meetacy.backend.feature.users.database.integration.users
import app.meetacy.di.builder.DIBuilder
import org.jetbrains.exposed.sql.Database

sealed interface DatabaseConfig {
    data class Connection(
        val url: String,
        val user: String,
        val password: String
    ) : DatabaseConfig

    data class Mock(val port: Int) : DatabaseConfig
}

fun DIBuilder.database() {
    databaseSingleton()
    // storages

    auth()
    email()
    telegram()
    files()
    friends()
    invitations()
    meetings()
    notifications()
    updates()
    users()
}

private fun DIBuilder.databaseSingleton() {
    val database by singleton {
        val databaseConfig: DatabaseConfig by getting

        with(databaseConfig) {
            when (this) {
                is DatabaseConfig.Connection -> Database.connect(
                    url = url,
                    user = user,
                    password = password
                )
                is DatabaseConfig.Mock -> Database.connect(
                    url = "jdbc:h2:mem:$port;DB_CLOSE_DELAY=-1",
                    driver = "org.h2.Driver"
                )
            }
        }
    }
}

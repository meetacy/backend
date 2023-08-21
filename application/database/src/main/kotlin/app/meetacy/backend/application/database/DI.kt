package app.meetacy.backend.application.database

import app.meetacy.backend.feature.auth.database.integration.auth
import app.meetacy.backend.feature.email.database.integration.email
import app.meetacy.backend.feature.files.database.integration.files
import app.meetacy.backend.feature.users.database.integration.types.getUsersViewsRepository
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
    getUsersViewsRepository() // TODO: move into users DI
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

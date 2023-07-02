@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.database

import app.meetacy.backend.di.DIBuilder
import org.jetbrains.exposed.sql.Database

fun DIBuilder.database() {
    val database by singleton {
        val databaseUrl: String by getting
        val databaseUser: String by getting
        val databasePassword: String by getting

        println("DATABASE $databaseUrl")

        Database.connect(
            url = databaseUrl,
            user = databaseUser,
            password = databasePassword
        )
    }
}

package app.meetacy.backend

import app.meetacy.backend.infrastructure.startMockEndpoints
import org.jetbrains.exposed.sql.Database

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    val databaseUrl = System.getenv("DATABASE_URL") ?: error("Please provide a database url")
    val databaseUser = System.getenv("DATABASE_USER") ?: ""
    val databasePassword = System.getenv("DATABASE_PASSWORD") ?: ""
    val filesBasePath = System.getenv("FILES_BASE_PATH") ?: "files"

    val database = Database.connect(
        databaseUrl,
        user = databaseUser,
        password = databasePassword
    )

    startMockEndpoints(filesBasePath, port, database, wait = true)
}

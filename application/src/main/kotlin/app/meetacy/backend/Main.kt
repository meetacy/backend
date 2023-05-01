package app.meetacy.backend

import app.meetacy.backend.infrastructure.startEndpoints
import org.jetbrains.exposed.sql.Database
import java.io.File

// todo почему есть Username, но нету Nickname?

// todo Добавить админов в митинг

// todo Заявки на вступление

// todo UserPreview? Кароч меньше инфы о пользователе в превьювах митинга например

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    val databaseUrl = System.getenv("DATABASE_URL") ?: error("Please provide a database url")
    val databaseUser = System.getenv("DATABASE_USER") ?: ""
    val databasePassword = System.getenv("DATABASE_PASSWORD") ?: ""
    val filesBasePath = System.getenv("FILES_BASE_PATH") ?: File(
        /* parent = */ System.getenv("user.dir"),
        /* child = */ "files"
    ).apply { mkdirs() }.absolutePath

    val filesSizeLimit = System.getenv("FILES_SIZE_LIMIT")?.toLongOrNull() ?: (100L * 1024 * 1024)

    val database = Database.connect(
        databaseUrl,
        user = databaseUser,
        password = databasePassword
    )

    startEndpoints(filesBasePath, filesSizeLimit, port, database, wait = true)
}

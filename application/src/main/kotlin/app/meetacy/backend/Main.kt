package app.meetacy.backend

import app.meetacy.backend.database.initDatabase
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.di
import app.meetacy.backend.infrastructure.prepareEndpoints
import app.meetacy.backend.run.runProductionServer
import app.meetacy.backend.types.file.FileSize
import app.meetacy.di.global.GlobalDI
import java.io.File

suspend fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    val databaseUrl = System.getenv("DATABASE_URL") ?: error("Please provide a database url")
    val databaseUser = System.getenv("DATABASE_USER") ?: ""
    val databasePassword = System.getenv("DATABASE_PASSWORD") ?: ""
    val filesBasePath = System.getenv("FILES_BASE_PATH") ?: File(
        /* parent = */ System.getenv("user.dir"),
        /* child = */ "files"
    ).apply { mkdirs() }.absolutePath
    val filesSizeLimit = System.getenv("FILES_SIZE_LIMIT")?.toLongOrNull() ?: (100L * 1024 * 1024)
    val webhookUrl = System.getenv("DISCORD_WEBHOOK_URL")

    runProductionServer(webhookUrl) {
        val di = di(
            port = port,
            databaseUrl = databaseUrl,
            databaseUser = databaseUser,
            databasePassword = databasePassword,
            filesBasePath = filesBasePath,
            filesSizeLimit = FileSize(filesSizeLimit)
        )
        GlobalDI.init(di)

        initDatabase(di.database)

        val server = prepareEndpoints(di)
        initialized = true
        server.start(wait = true)
    }
}

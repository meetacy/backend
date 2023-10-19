package app.meetacy.backend

import app.meetacy.backend.application.database.DatabaseConfig
import app.meetacy.backend.application.endpoints.prepareEndpoints
import app.meetacy.backend.di.buildDI
import app.meetacy.backend.run.runProductionServer
import app.meetacy.backend.types.files.FileSize
import java.io.File

suspend fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    val databaseUrl = System.getenv("DATABASE_URL")
    val databaseUser = System.getenv("DATABASE_USER") ?: ""
    val databasePassword = System.getenv("DATABASE_PASSWORD") ?: ""
    val filesBasePath = System.getenv("FILES_BASE_PATH") ?: File(
        /* parent = */ System.getenv("user.dir"),
        /* child = */ "files"
    ).apply { mkdirs() }.absolutePath
    val filesSizeLimit = System.getenv("FILES_SIZE_LIMIT")?.toLongOrNull() ?: (99L * 1024 * 1024)
    val useMockDatabase = System.getenv("USE_MOCK_DATABASE")?.toBoolean() ?: (databaseUrl == null)
    val webhookUrl = System.getenv("DISCORD_WEBHOOK_URL")

    val databaseConfig = if (useMockDatabase) {
        DatabaseConfig.Mock(port)
    } else {
        DatabaseConfig.Connection(
            url = databaseUrl ?: error("Please specify DATABASE_URL env variable, or set USE_MOCK_DATABASE to `true`"),
            user = databaseUser,
            password = databasePassword
        )
    }

    runProductionServer(webhookUrl) {
        val di = buildDI(
            port = port,
            databaseConfig = databaseConfig,
            fileBasePath = filesBasePath,
            fileSizeLimit = FileSize(filesSizeLimit)
        )

        val server = prepareEndpoints(di)
        initialized = true
        server.start(wait = true)
    }
}


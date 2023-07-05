@file:Suppress("NAME_SHADOWING", "UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure

import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.di
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.infrastructure.database.DatabaseConfig
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.files.files
import app.meetacy.backend.infrastructure.integrations.integrations
import app.meetacy.backend.types.file.FileSize
import java.io.File

val DI.port: Int by Dependency
val DI.databaseConfig: DatabaseConfig by Dependency
val DI.filesBasePath: String by Dependency
val DI.filesLimitPerUser: FileSize by Dependency

fun di(
    port: Int,
    databaseUrl: String,
    databaseUser: String,
    databasePassword: String,
    filesBasePath: String,
    filesSizeLimit: FileSize
) = di {
    val port by constant(port)
    val databaseConfig by constant(
        DatabaseConfig(databaseUrl, databaseUser, databasePassword)
    )
    val filesBasePath by constant(filesBasePath)
    val filesSizeLimit by constant(filesSizeLimit)

    database()
    integrations()
}

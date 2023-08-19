@file:Suppress("NAME_SHADOWING")

package app.meetacy.backend.infrastructure

import app.meetacy.backend.application.database.database
import app.meetacy.backend.application.endpoints.endpoints
import app.meetacy.backend.application.usecase.usecase
import app.meetacy.backend.types.file.FileSize
import app.meetacy.di.DI
import app.meetacy.di.builder.di
import app.meetacy.di.dependency.Dependency

val DI.port: Int by Dependency

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
        app.meetacy.backend.application.database.DatabaseConfig(databaseUrl, databaseUser, databasePassword)
    )
    val filesBasePath by constant(filesBasePath)
    val filesSizeLimit by constant(filesSizeLimit)

    endpoints()
    usecase()
    database()
}

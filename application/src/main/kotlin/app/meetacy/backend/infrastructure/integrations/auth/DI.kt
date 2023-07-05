@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.auth

import app.meetacy.backend.database.integration.types.DatabaseAuthRepository
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.usecase.types.AuthRepository

val DI.authRepository: AuthRepository by Dependency

fun DIBuilder.auth() {
    val authRepository by singleton<AuthRepository> { DatabaseAuthRepository(database) }
}

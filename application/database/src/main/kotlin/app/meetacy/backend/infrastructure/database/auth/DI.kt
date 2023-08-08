package app.meetacy.backend.infrastructure.database.auth

import app.meetacy.backend.feature.auth.database.TokensStorage
import app.meetacy.backend.database.integration.types.DatabaseAuthRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.tokensStorage: TokensStorage by Dependency
val DI.authRepository: AuthRepository by Dependency

fun DIBuilder.auth() {
    val tokensStorage by singleton { TokensStorage(database) }
    val authRepository by singleton<AuthRepository> { DatabaseAuthRepository(database) }
}

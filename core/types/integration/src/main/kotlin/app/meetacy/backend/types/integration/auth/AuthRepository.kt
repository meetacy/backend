package app.meetacy.backend.types.integration.auth

import app.meetacy.backend.feature.auth.database.TokensStorage
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.di.builder.DIBuilder
import org.jetbrains.exposed.sql.Database

fun DIBuilder.authRepository() {
    val authRepository by singleton<AuthRepository> { DatabaseAuthRepository(db = get()) }
}

class DatabaseAuthRepository(db: Database) : AuthRepository {
    private val tokensStorage = TokensStorage(db)
    override suspend fun authorize(accessIdentity: AccessIdentity) = tokensStorage.checkToken(accessIdentity)
}

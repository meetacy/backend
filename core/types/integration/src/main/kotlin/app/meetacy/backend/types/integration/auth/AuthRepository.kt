package app.meetacy.backend.types.integration.auth

import app.meetacy.backend.feature.auth.database.TokensStorage
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.di.builder.DIBuilder
import org.jetbrains.exposed.sql.Database

internal fun DIBuilder.authRepository() {
    val authRepository by singleton<AuthRepository> {
        val tokensStorage: TokensStorage by getting

        object : AuthRepository {
            override suspend fun authorize(
                accessIdentity: AccessIdentity
            ) = tokensStorage.checkToken(accessIdentity)
        }
    }
}

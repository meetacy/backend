package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.auth.TokensTable
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.usecase.types.AuthRepository
import org.jetbrains.exposed.sql.Database

class DatabaseAuthRepository(private val db: Database) : AuthRepository {
    override suspend fun authorize(accessToken: AccessToken): AuthRepository.Result =
        when (val result = TokensTable(db).getToken(accessToken)?.ownerId) {
            null -> AuthRepository.Result.TokenInvalid
            else -> AuthRepository.Result.Success(result)
        }
}
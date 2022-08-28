package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.auth.TokensTable
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.usecase.types.AuthRepository
import org.jetbrains.exposed.sql.Database

class DatabaseAuthRepository(private val db: Database) : AuthRepository {
    private val tokensTable = TokensTable(db)
    override suspend fun authorize(accessToken: AccessToken): AuthRepository.Result =
        when (val result = tokensTable.getToken(accessToken)?.ownerId) {
            null -> AuthRepository.Result.TokenInvalid
            else -> AuthRepository.Result.Success(result)
        }
}

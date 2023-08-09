package app.meetacy.backend.feature.auth.database.integration.types

import app.meetacy.backend.feature.auth.database.TokensStorage
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.usecase.types.AuthRepository
import org.jetbrains.exposed.sql.Database

class DatabaseAuthRepository(db: Database) : AuthRepository {
    private val tokensStorage = TokensStorage(db)
    override suspend fun authorize(accessIdentity: AccessIdentity) = tokensStorage.checkToken(accessIdentity)
}

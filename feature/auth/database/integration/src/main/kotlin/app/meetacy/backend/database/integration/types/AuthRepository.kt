package app.meetacy.backend.database.integration.types

import app.meetacy.backend.feature.auth.database.TokensStorage
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.AuthRepository
import org.jetbrains.exposed.sql.Database

class DatabaseAuthRepository(db: Database) : AuthRepository {
    private val tokensStorage = TokensStorage(db)
    override suspend fun authorize(accessIdentity: AccessIdentity) = tokensStorage.checkToken(accessIdentity)
}

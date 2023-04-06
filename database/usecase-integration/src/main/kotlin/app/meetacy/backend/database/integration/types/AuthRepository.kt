package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.auth.TokensTable
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.usecase.types.AuthRepository
import org.jetbrains.exposed.sql.Database

class DatabaseAuthRepository(db: Database) : AuthRepository {
    private val tokensTable = TokensTable(db)
    override suspend fun authorize(accessIdentity: AccessIdentity) = tokensTable.checkToken(accessIdentity)
}

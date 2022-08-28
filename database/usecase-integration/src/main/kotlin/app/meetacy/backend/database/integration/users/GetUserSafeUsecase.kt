package app.meetacy.backend.database.integration.users

import app.meetacy.backend.database.auth.TokensTable
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.usecase.users.GetUserSafeUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetUserSafeStorage(private val db: Database) : GetUserSafeUsecase.Storage {
    override fun getTokenOwnerId(token: AccessToken) = TokensTable(db).getToken(token)?.ownerId
}
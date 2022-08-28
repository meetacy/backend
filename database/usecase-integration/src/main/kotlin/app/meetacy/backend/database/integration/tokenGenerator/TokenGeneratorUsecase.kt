package app.meetacy.backend.database.integration.tokenGenerator

import app.meetacy.backend.database.auth.TokensTable
import app.meetacy.backend.database.integration.users.DatabaseCreateUserStorage
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase
import app.meetacy.backend.usecase.types.HashGenerator
import app.meetacy.backend.usecase.users.CreateUserUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGenerateTokenStorage(private val hashGenerator: HashGenerator, private val db: Database) : GenerateTokenUsecase.Storage {
    private val createUserUsecase = CreateUserUsecase(hashGenerator, DatabaseCreateUserStorage(db))
    private val tokensTable = TokensTable(db)

    override suspend fun createUser(nickname: String): UserId =
        createUserUsecase.createUser(nickname)

    override suspend fun addToken(id: UserId, token: AccessToken) =
        tokensTable.addToken(id, token)
}
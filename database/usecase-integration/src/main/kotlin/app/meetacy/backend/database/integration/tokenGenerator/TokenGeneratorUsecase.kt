package app.meetacy.backend.database.integration.tokenGenerator

import app.meetacy.backend.database.auth.TokensTable
import app.meetacy.backend.database.integration.users.DatabaseCreateUserStorage
import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserId
import app.meetacy.backend.types.UserIdentity
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase
import app.meetacy.backend.usecase.types.HashGenerator
import app.meetacy.backend.usecase.users.CreateUserUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGenerateTokenStorage(hashGenerator: HashGenerator, db: Database) : GenerateTokenUsecase.Storage {
    private val createUserUsecase = CreateUserUsecase(hashGenerator, DatabaseCreateUserStorage(db))
    private val tokensTable = TokensTable(db)

    override suspend fun createUser(nickname: String): UserIdentity =
        createUserUsecase.createUser(nickname)

    override suspend fun addToken(accessIdentity: AccessIdentity) =
        tokensTable.addToken(UserId(accessIdentity.userId.long), AccessToken(accessIdentity.accessToken.string))
}
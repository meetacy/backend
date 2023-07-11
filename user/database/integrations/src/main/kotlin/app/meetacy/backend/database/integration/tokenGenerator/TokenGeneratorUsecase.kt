package app.meetacy.backend.database.integration.tokenGenerator

import app.meetacy.backend.database.auth.TokensStorage
import app.meetacy.backend.database.integration.users.create.DatabaseCreateUserStorage
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase
import app.meetacy.backend.usecase.types.HashGenerator
import app.meetacy.backend.usecase.auth.CreateUserUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGenerateTokenStorage(hashGenerator: HashGenerator, db: Database) : GenerateTokenUsecase.Storage {
    private val createUserUsecase = CreateUserUsecase(hashGenerator, DatabaseCreateUserStorage(db))
    private val tokensStorage = TokensStorage(db)

    override suspend fun createUser(nickname: String): UserId =
        createUserUsecase.createUser(nickname)

    override suspend fun addToken(accessIdentity: AccessIdentity) =
        tokensStorage.addToken(accessIdentity)
}
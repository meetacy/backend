package app.meetacy.backend.database.integration.tokenGenerator

import app.meetacy.backend.feature.auth.database.TokensStorage
import app.meetacy.backend.database.integration.users.create.DatabaseCreateUserStorage
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.auth.CreateUserUsecase
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGenerateTokenStorage(hashGenerator: AccessHashGenerator, db: Database) : GenerateTokenUsecase.Storage {
    private val createUserUsecase = CreateUserUsecase(hashGenerator, DatabaseCreateUserStorage(db))
    private val tokensStorage = TokensStorage(db)

    override suspend fun createUser(nickname: String): UserId =
        createUserUsecase.createUser(nickname)

    override suspend fun addToken(accessIdentity: AccessIdentity) =
        tokensStorage.addToken(accessIdentity)
}
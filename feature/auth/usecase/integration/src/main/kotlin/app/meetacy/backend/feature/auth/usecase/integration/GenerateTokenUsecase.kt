package app.meetacy.backend.feature.auth.usecase.integration

import app.meetacy.backend.feature.auth.database.TokensStorage
import app.meetacy.backend.feature.auth.usecase.CreateUserUsecase
import app.meetacy.backend.feature.auth.usecase.GenerateTokenUsecase
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.utf8Checker.Utf8Checker
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.generateTokenUsecase() {
    val generateTokenUsecase by singleton {
        val tokensStorage: TokensStorage by getting
        val createUserUsecase: CreateUserUsecase by getting
        val accessHashGenerator: AccessHashGenerator by getting
        val utf8Checker: Utf8Checker by getting

        val storage = DatabaseGenerateTokenStorage(tokensStorage, createUserUsecase)
        GenerateTokenUsecase(storage, accessHashGenerator, utf8Checker)
    }
}

private class DatabaseGenerateTokenStorage(
    private val tokensStorage: TokensStorage,
    private val createUserUsecase: CreateUserUsecase
) : GenerateTokenUsecase.Storage {

    override suspend fun createUser(nickname: String): UserId =
        createUserUsecase.createUser(nickname)

    override suspend fun addToken(accessIdentity: AccessIdentity) =
        tokensStorage.addToken(accessIdentity)
}
